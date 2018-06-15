package org.carbon

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.carbon.parser.CarbonLexer
import org.carbon.parser.CarbonParser
import org.carbon.parser.CarbonParserBaseVisitor
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonRootExpression

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
fun compile(input: CharStream, environment: CarbonRootExpression) {
    val parsed = parseInput(input)

    if (parsed.numberOfSyntaxErrors > 0) {
        println("Syntax errors!")
        return
    }

    for (statement in parsed.compilationUnit().statement()) {
        val expression = CompilerVisitor(environment).visit(statement.expression())
        if (expression == null) {
            println("Failed to compile: " + statement.text)
        } else {
            environment.putMember(statement.declaration().text, expression)
        }
    }

    println(environment.members)
}

private fun parseInput(input: CharStream): CarbonParser {
    val lexer = CarbonLexer(input)
    val tokens = CommonTokenStream(lexer)
    val parser = CarbonParser(tokens)
    return parser
}

fun compileExpression(input: CharStream, environment: CarbonRootExpression) : CarbonExpression? {
    val parsed = parseInput(input)
    val expressionAst = parsed.expression()
    return CompilerVisitor(environment).visit(expressionAst)
}

class CompilerVisitor(environment: CarbonRootExpression) : CarbonParserBaseVisitor<CarbonExpression>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext?): CarbonExpression {
        val value = ctx!!.text.toInt()
        return CarbonInteger(value)
    }
}