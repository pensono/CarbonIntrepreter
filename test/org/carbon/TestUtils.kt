package org.carbon

import org.antlr.v4.runtime.CharStreams
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonMonad
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
    return evaluate(input, RootScope())!!
}

fun envTest(input: String, member: String, expected: CarbonExpression) {
    Assert.assertEquals(expected, testEnvEval(input, member))
}

fun testEnvEval(input: String, member: String) : CarbonExpression {
    val scope = compile(CharStreams.fromString(input), RootScope())!!
    return scope.lookupName(member)!!.eval()
}

fun mutTest(input: String, mutator: String, member: String, expected: CarbonExpression) {
    val scope = compile(CharStreams.fromString(input), RootScope())!!
    val monad = scope.lookupName(mutator)!!.eval() as CarbonMonad

    monad.execute()

    val actual = scope.lookupName(member)!!.eval()
    Assert.assertEquals(expected, actual)
}