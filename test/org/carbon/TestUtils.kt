package org.carbon

import org.antlr.v4.runtime.CharStreams
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonRootExpression
import org.junit.Assert

/**
 * @author Ethan Shea
 * @date 6/15/2018
 */

fun exprTest(input: String, expr: CarbonExpression) {
    Assert.assertEquals(expr, compileExpression(CharStreams.fromString(input), CarbonRootExpression())!!.eval())
}