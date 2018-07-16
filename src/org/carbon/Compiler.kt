package org.carbon

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.carbon.parser.CarbonLexer
import org.carbon.parser.CarbonParser
import org.carbon.parser.CarbonParserBaseVisitor
import org.carbon.runtime.*
import org.carbon.syntax.*

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
fun compile(input: CharStream, environment: RootScope) : RootScope? {
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

fun compileExpression(input: CharStream) : Node? {
    val parser = preparseInput(input)
    val expressionAst = parser.expression()
    return NodeVisitor(RootScope()).visit(expressionAst)
}

class NodeVisitor(val lexicalScope: CarbonScope) : CarbonParserBaseVisitor<Node>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): Node {
        val value = ctx.text.toInt()
        return IntegerNode(value)
    }

    override fun visitInfixExpression(ctx: CarbonParser.InfixExpressionContext): Node {
        val lhs = ctx.lhs.accept(this)
        val rhs = ctx.rhs.accept(this)

        return AppliedNode(ctx.sourceInterval, MemberNode(ctx.sourceInterval, lhs, ctx.op.text), listOf(rhs))
    }

    override fun visitDotExpression(ctx: CarbonParser.DotExpressionContext): Node {
        val base = ctx.base.accept(this)

        return MemberNode(ctx.sourceInterval, base, ctx.identifier().text)
    }

    override fun visitApplicationExpression(ctx: CarbonParser.ApplicationExpressionContext): Node {
        val base = ctx.base.accept(this)
//        val args = ctx.arguments.map { arg -> arg.accept(this) }

        // Kinda trash, but it's the best I can do
        // https://stackoverflow.com/questions/51074953/antlr-parse-with-missing-elements
        val args = mutableListOf<Node?>()
        if (ctx.first_argument != null) {
            args.add(ctx.first_argument.accept(this))
        } else if (ctx.other_arguments.isNotEmpty()){
            args.add(null)
        }
        ctx.other_arguments.forEach { a -> args.add(a?.accept(this)) }

        // This code here is not very good, should be rewritten
        return AppliedNode(ctx.sourceInterval, base, args)
    }

    override fun visitTypeLiteral(ctx: CarbonParser.TypeLiteralContext): Node {
        val members = toParameterList(lexicalScope, ctx.members!!)
        val derivedMemberExpressions = ctx.derivedMembers.map { s -> visitStatement(lexicalScope, s) }.filterNotNull().toMap() // Is this the right way to deal with nulls?
        // Visit statement always returns with no actual parameters... is this a larger design issue?
        val derivedMembers = derivedMemberExpressions.mapValues { e -> e.value.body }

        return ArbitraryTypeNode(members, derivedMembers)
    }

    override fun visitIdentifier(ctx: CarbonParser.IdentifierContext): Node {
        return IdentifierNode(ctx.sourceInterval, ctx.text)
    }
}

// Should this return a CarbonExpression and not an ScalarExpression?
private fun visitStatement(lexicalScope: CarbonScope, ctx: CarbonParser.StatementContext) : Pair<String, ScalarExpression>? {
    val  body = NodeVisitor(lexicalScope).visit(ctx.expression()) ?: return null
    val parameterNames = if (ctx.hasParameterList != null) {
        toParameterList(lexicalScope, ctx.parameters!!).map { p -> p.first}
    } else {
        listOf()
    }
    return Pair(ctx.declaration().text, ScalarExpression(lexicalScope, body, parameterNames))
}

private fun toParameterList(scope: CarbonScope, paramsCtx: List<CarbonParser.ParameterContext>): List<Pair<String, Node>> {
    return paramsCtx.map { c ->
        Pair(
                c.name?.text ?: c.type().text!!,
                c.type().accept(NodeVisitor(scope))
        )
    }
}