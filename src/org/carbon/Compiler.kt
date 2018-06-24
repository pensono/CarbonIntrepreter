package org.carbon

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.carbon.parser.CarbonLexer
import org.carbon.parser.CarbonParser
import org.carbon.parser.CarbonParserBaseVisitor
import org.carbon.runtime.*

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
fun compile(input: CharStream, environment: CarbonRootScope) : CarbonRootScope? {
    val parser = preparseInput(input)

    if (parser.numberOfSyntaxErrors > 0) {
        println("Syntax errors!")
        return null
    }

    for (statement in parser.compilationUnit().statement()) {
        var expression = ExpressionVisitor.visit(statement.expression())
        if (expression == null) {
            println("Failed to compile: " + statement.text)
        } else {
            if (statement.parameters.size > 0) {
                val parameterNames = toParameterList(statement.parameters!!).map {p -> p.first}
                expression = FunctionExpression(environment, parameterNames, expression)
            }
            environment.putMember(statement.declaration().text, expression)
        }
    }

    // TODO return a modified version of the environment?
    return environment
}

private fun preparseInput(input: CharStream): CarbonParser {
    val lexer = CarbonLexer(input)
    val tokens = CommonTokenStream(lexer)
    val parser = CarbonParser(tokens)
    return parser
}

fun compileExpression(input: CharStream) : CarbonExpression? {
    val parser = preparseInput(input)
    val expressionAst = parser.expression()
    return ExpressionVisitor.visit(expressionAst)
}

object ExpressionVisitor : CarbonParserBaseVisitor<CarbonExpression>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonExpression {
        val value = ctx.text.toInt()
        return CarbonInteger(value)
    }

    override fun visitInfixExpression(ctx: CarbonParser.InfixExpressionContext): CarbonExpression {
        val lhs = ctx.lhs.accept(ExpressionVisitor)
        val rhs = ctx.rhs.accept(ExpressionVisitor)

        return AppliedExpression(MemberExpression(lhs, ctx.op.text), listOf(rhs))
    }

    override fun visitDotExpression(ctx: CarbonParser.DotExpressionContext): CarbonExpression {
        val base = ctx.base.accept(ExpressionVisitor)

        return MemberExpression(base, ctx.identifier().text)
    }

    override fun visitApplicationExpression(ctx: CarbonParser.ApplicationExpressionContext): CarbonExpression {
        val base = ctx.base.accept(ExpressionVisitor)
        val args = ctx.arguments.map { arg -> arg.accept(ExpressionVisitor) }

        return AppliedExpression(base, args)
    }

    override fun visitTypeLiteral(ctx: CarbonParser.TypeLiteralContext): CarbonExpression {
        val members = toParameterList(ctx.members!!)

        return CarbonArbitraryType(members)
    }

    override fun visitIdentifier(ctx: CarbonParser.IdentifierContext): CarbonExpression {
        return IdentifierExpression(ctx.text)
    }
}

private fun toParameterList(paramsCtx: List<CarbonParser.ParameterContext>): List<Pair<String, CarbonExpression>> {
    return paramsCtx.map { c ->
        Pair(
                c.name?.text ?: c.type().text!!,
                c.type().accept(ExpressionVisitor)
        )
    }
}