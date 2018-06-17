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

//    @Test
//    fun applicationMemberTest() {
//        exprTest("{A:Integer}(2).A", CarbonInteger(2))
//    }

    // TODO test multiple parameters
}