package org.carbon

import org.carbon.runtime.CarbonInteger
import org.junit.Assert.*
import org.junit.Test

/**
 * @author Ethan
 */
class ExpressionTests {
    @Test
    fun basicIdentifierTest() {
        val env = testEnv("A=3;")
        assertEquals(CarbonInteger(3), env!!.getMember("A")!!)
    }

    @Test
    fun applicationSingleArg() {
        exprTest("{A:Integer}(2).A", CarbonInteger(2))
    }

    @Test
    fun applicationMultipleArg() {
        exprTest("{A:Integer,B:Integer}(2,3).A", CarbonInteger(2))
        exprTest("{A:Integer,B:Integer}(2,3).B", CarbonInteger(3))
    }
}