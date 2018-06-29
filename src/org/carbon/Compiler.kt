package org.carbon

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.carbon.parser.CarbonLexer
import org.carbon.parser.CarbonParser
import org.carbon.parser.CarbonParserBaseVisitor
import org.carbon.runtime.*
import org.carbon.test.TestBaseVisitor
import org.carbon.test.TestParser

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
        val compiledStatement = visitStatement(environment, statement) ?: throw CompilationException("Failed to compile " + statement.text, statement.sourceInterval)
        environment.putMember(compiledStatement.first, compiledStatement.second)
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
    return ExpressionVisitor(CarbonRootScope()).visit(expressionAst)
}

class ExpressionVisitor(val lexicalScope: CarbonScope) : CarbonParserBaseVisitor<CarbonExpression>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonExpression {
        val value = ctx.text.toInt()
        return CarbonInteger(value)
    }

    override fun visitInfixExpression(ctx: CarbonParser.InfixExpressionContext): CarbonExpression {
        val lhs = ctx.lhs.accept(this)
        val rhs = ctx.rhs.accept(this)

        return AppliedExpression(ctx.sourceInterval, MemberExpression(ctx.sourceInterval, lhs, ctx.op.text), listOf(rhs))
    }

    override fun visitDotExpression(ctx: CarbonParser.DotExpressionContext): CarbonExpression {
        val base = ctx.base.accept(this)

        return MemberExpression(ctx.sourceInterval, base, ctx.identifier().text)
    }

    override fun visitApplicationExpression(ctx: CarbonParser.ApplicationExpressionContext): CarbonExpression {
        val base = ctx.base.accept(this)
//        val args = ctx.arguments.map { arg -> arg.accept(this) }

        // This code is kinda trash
        val args = mutableListOf<CarbonExpression?>()
        var i = 2 // Start after the (
        while (i < ctx.children.size) { // end before the )
            val child = ctx.getChild(i)
            if (child.text == "," || child.text == ")") {
                args.add(null)
                i++
            } else {
                args.add(ctx.getChild(i).accept(this))
                i += 2
            }
        }

        // This code here is not very good, should be rewritten
        return AppliedExpression(ctx.sourceInterval, base, args)
    }

    override fun visitTypeLiteral(ctx: CarbonParser.TypeLiteralContext): CarbonExpression {
        val members = toParameterList(lexicalScope, ctx.members!!)
        val derivedMembers = ctx.derivedMembers.map { s -> visitStatement(lexicalScope, s) }.filterNotNull() // Is this the right way to deal with nulls?

        val result = CarbonArbitraryType(lexicalScope, members, derivedMembers)
        return result
    }

    override fun visitIdentifier(ctx: CarbonParser.IdentifierContext): CarbonExpression {
        return IdentifierExpression(ctx.sourceInterval, ctx.text)
    }
}

private fun visitStatement(scope: CarbonScope, ctx: CarbonParser.StatementContext) : Pair<String, CarbonExpression>? {
    var expression = ExpressionVisitor(scope).visit(ctx.expression()) ?: return null
    if (ctx.hasParameterList != null) {
        val parameterNames = toParameterList(scope, ctx.parameters!!).map {p -> p.first}
        expression = FunctionExpression(scope, parameterNames, expression)
    }
    return Pair(ctx.declaration().text, expression)
}

private fun toParameterList(scope: CarbonScope, paramsCtx: List<CarbonParser.ParameterContext>): List<Pair<String, CarbonExpression>> {
    return paramsCtx.map { c ->
        Pair(
                c.name?.text ?: c.type().text!!,
                c.type().accept(ExpressionVisitor(scope))
        )
    }
}