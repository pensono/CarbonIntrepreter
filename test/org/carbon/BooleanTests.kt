package org.carbon

import org.carbon.runtime.CarbonBoolean
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Ethan Shea
 *
 * @date 6/13/2018
 */
class BooleanTests {
    @Test
    fun literals() {
        exprTest("True", CarbonBoolean(true))
        exprTest("False", CarbonBoolean(false))
    }

    @Test
    fun expression() {
        exprTest("Boolean(True)", CarbonBoolean(true))
        exprTest("Boolean(False)", CarbonBoolean(false))
        envTest("""
            S = True;
            R = Boolean(S);
        """, "R", CarbonBoolean(true))
    }

    @Test
    fun and() {
        exprTest("False && False", CarbonBoolean(false))
        exprTest("True && False", CarbonBoolean(false))
        exprTest("False && True", CarbonBoolean(false))
        exprTest("True && True", CarbonBoolean(true))
    }
}