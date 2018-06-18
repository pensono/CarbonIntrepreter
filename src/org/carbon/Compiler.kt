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
fun compile(input: CharStream, environment: CarbonScope) : CarbonRootScope? {
    val parser = preparseInput(input)

    if (parser.numberOfSyntaxErrors > 0) {
        println("Syntax errors!")
        return null
    }

    for (statement in parser.compilationUnit().statement()) {
        val expression = CompilerVisitor(environment).visit(statement.expression())
        if (expression == null) {
            println("Failed to compile: " + statement.text)
        } else {
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

fun compileExpression(input: CharStream, environment: CarbonScope) : CarbonExpression? {
    val parser = preparseInput(input)
    val expressionAst = parser.expression()
    return CompilerVisitor(environment).visit(expressionAst)
}

class CompilerVisitor(private val environment: CarbonScope) : CarbonParserBaseVisitor<CarbonExpression>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonExpression {
        val value = ctx.text.toInt()
        return CarbonInteger(value)
    }

    override fun visitInfixExpression(ctx: CarbonParser.InfixExpressionContext): CarbonExpression {
        val lhs = ctx.lhs.accept(this)
        val rhs = ctx.rhs.accept(this)

        return AppliedExpression(MemberExpression(lhs, ctx.op.text), listOf(rhs))
    }

    override fun visitDotExpression(ctx: CarbonParser.DotExpressionContext): CarbonExpression {
        val base = ctx.base.accept(this)

        return MemberExpression(base, ctx.identifier().text)
    }

    override fun visitApplicationExpression(ctx: CarbonParser.ApplicationExpressionContext): CarbonExpression {
        val base = ctx.base.accept(this)
        val args = ctx.arguments.map { arg -> arg.accept(this) }

        return AppliedExpression(base, args)
    }

    override fun visitTypeLiteral(ctx: CarbonParser.TypeLiteralContext): CarbonExpression {
        val members = ctx.members.map{c -> Pair(
                c.name?.text ?: c.type().text!!,
                c.type().accept(this) as CarbonType
        )}

        return CarbonArbitraryType(members)
    }

    override fun visitIdentifier(ctx: CarbonParser.IdentifierContext): CarbonExpression {
        return environment.getMember(ctx.text!!)!! // This is weak sauce. Doesn't handle errors or actually do lookups, or handle binding after the parse stage
    }
}