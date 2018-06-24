package org.carbon

import org.antlr.v4.runtime.CharStreams
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonRootScope
import org.junit.Assert

/**
 * @author Ethan Shea
 * @date 6/15/2018
 */

fun exprTest(input: String, expr: CarbonExpression) {
    Assert.assertEquals(expr, testEval(input))
}

fun testEval(input: String) : CarbonExpression {
    val scope = CarbonRootScope()
    return compileExpression(CharStreams.fromString(input))!!.eval(scope)
}

fun envTest(input: String, member: String, expr: CarbonExpression) {
    Assert.assertEquals(expr, testEnvEval(input, member))
}

fun testEnvEval(input: String, member: String) : CarbonExpression {
    val scope = compile(CharStreams.fromString(input), CarbonRootScope())!!
    return scope.getMember(member)!!.eval(scope)
}