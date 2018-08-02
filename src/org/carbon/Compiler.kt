package org.carbon

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.carbon.parser.CarbonLexer
import org.carbon.parser.CarbonParser
import org.carbon.parser.CarbonParserBaseVisitor
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope
import org.carbon.runtime.RootScope
import org.carbon.syntax.*
import javax.swing.plaf.nimbus.State

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
        val compiledStatement = StatementVisitor(environment).visit(statement) ?: throw CompilationException("Failed to compile " + statement.text, statement.sourceInterval)
        val body = CarbonExpression(environment, compiledStatement.body, formalParameters = compiledStatement.formalParameters)
        environment.putMember(compiledStatement.name, body)
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

fun evaluate(input: String, scope: CarbonScope) : CarbonExpression? {
    val expression = compileExpression(CharStreams.fromString(input))
    return expression?.link(scope)?.eval()
}

class NodeVisitor(val lexicalScope: CarbonScope) : CarbonParserBaseVisitor<Node>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): Node {
        val value = ctx.text.toInt()
        return IntegerNode(value)
    }

    override fun visitStringLiteral(ctx: CarbonParser.StringLiteralContext): Node {
        val value = ctx.text.substring(1, ctx.text.length - 1) // Chop off the quotes at the ends
                        .replace("\\n", "\n")
        return StringNode(value)
    }

    override fun visitInfixExpression(ctx: CarbonParser.InfixExpressionContext): Node {
        val lhs = ctx.lhs.accept(this)
        val rhs = ctx.rhs.accept(this)
        val operationName = ctx.getChild(1).text

        return AppliedNode(ctx.sourceInterval, MemberNode(ctx.sourceInterval, lhs, operationName), listOf(rhs))
    }

    override fun visitDotExpression(ctx: CarbonParser.DotExpressionContext): Node {
        val base = ctx.base.accept(this)

        return MemberNode(ctx.sourceInterval, base, ctx.identifier().text)
    }

    override fun visitApplicationExpression(ctx: CarbonParser.ApplicationExpressionContext): Node {
        val base = ctx.base.accept(this)

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

        // This transformation code is bad.
        assert(!ctx.derivedMembers.contains(null))

        val statementVisitor = StatementVisitor(lexicalScope)
        val derivedMembers = ctx.derivedMembers.map(statementVisitor::visit).filterNotNull() // Null filter should be a no-op

        return ArbitraryTypeNode(members, derivedMembers)
    }

    override fun visitIdentifier(ctx: CarbonParser.IdentifierContext): Node {
        return IdentifierNode(ctx.sourceInterval, ctx.text)
    }
}

class StatementVisitor(val lexicalScope: CarbonScope) : CarbonParserBaseVisitor<Statement>() {
    override fun visitStatement(ctx: CarbonParser.StatementContext): Statement {
        var body = NodeVisitor(lexicalScope).visit(ctx.default_expression)

        // Wrap body in BranchNodes for each guard (if any)
        for (guard in ctx.guards.reversed()) {
            val predicate = NodeVisitor(lexicalScope).visit(guard.predicate)
            val branchBody = NodeVisitor(lexicalScope).visit(guard.body)

            body = BranchNode(predicate, branchBody, body)
        }

        val parameterNames = if (ctx.hasParameterList != null) {
            toParameterList(lexicalScope, ctx.parameters!!).map { p -> p.first} // Ignore the types for now
        } else {
            listOf()
        }
        return Statement(ctx.variable_declaration().text, body, parameterNames)
    }
}

private fun toParameterList(lexicalScope: CarbonScope, paramsCtx: List<CarbonParser.ParameterContext>): List<Pair<String, Node>> {
    return paramsCtx.map { c ->
        Pair(
                c.name?.text ?: c.type().text!!,
                c.type().accept(NodeVisitor(lexicalScope))
        )
    }
}