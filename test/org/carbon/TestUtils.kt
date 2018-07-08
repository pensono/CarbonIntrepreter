package org.carbon

import org.antlr.v4.runtime.CharStreams
import org.carbon.runtime.CarbonExpression
import org.carbon.syntax.Node
import org.carbon.runtime.RootScope
import org.junit.Assert

/**
 * @author Ethan Shea
 * @date 6/15/2018
 */

fun exprTest(input: String, expected: CarbonExpression) {
    Assert.assertEquals(expected, testEval(input))
}

fun testEval(input: String) : CarbonExpression {
    val scope = RootScope()
    return compileExpression(CharStreams.fromString(input))!!.eval(scope).eval_reduce(scope) // TODO two evals... something's wrong here...
}

fun envTest(input: String, member: String, expected: CarbonExpression) {
    Assert.assertEquals(expected, testEnvEval(input, member))
}

fun testEnvEval(input: String, member: String) : CarbonExpression {
    val scope = compile(CharStreams.fromString(input), RootScope())!!
    return scope.lookupName(member)!!.eval_reduce(scope)
}