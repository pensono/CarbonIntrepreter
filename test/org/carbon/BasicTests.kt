package org.carbon

import org.antlr.v4.runtime.CharStreams
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonRootExpression
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Ethan Shea
 * *
 * @date 6/13/2018
 */
class BasicTests {
    @Test
    fun numerals() {
        doTest("1", CarbonInteger(1))
        doTest("34", CarbonInteger(34))
        doTest("137", CarbonInteger(137))
        doTest("-1", CarbonInteger(-1))
        doTest("-34", CarbonInteger(-34))
        doTest("-137", CarbonInteger(-137))
        // TODO Decimal numbers
    }

    @Test
    fun infixOperators() {
        doTest("1+1", CarbonInteger(2))
        doTest("34+57", CarbonInteger(91))
        doTest("-34+57", CarbonInteger(23))
    }

    fun doTest(input: String, expr: CarbonExpression) {
        assertEquals(expr, compileExpression(CharStreams.fromString(input), CarbonRootExpression())!!.eval())
    }
}