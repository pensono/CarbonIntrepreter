package org.carbon

import org.carbon.runtime.CarbonBoolean
import org.carbon.runtime.CarbonInteger
import org.junit.Test

/**
 * @author Ethan Shea
 * *
 * @date 6/13/2018
 */
class IntegerTests {
    @Test
    fun literals() {
        exprTest("1", CarbonInteger(1))
        exprTest("34", CarbonInteger(34))
        exprTest("137", CarbonInteger(137))
        exprTest("-1", CarbonInteger(-1))
        exprTest("-34", CarbonInteger(-34))
        exprTest("-137", CarbonInteger(-137))
        // TODO Decimal numbers
    }

    @Test
    fun expression() {
        exprTest("Integer(1)", CarbonInteger(1))
        envTest("""
            I = 3;
            R = Integer(I);
        """, "R", CarbonInteger(3))
    }


    @Test
    fun infixOperators() {
        exprTest("1+1", CarbonInteger(2))
        exprTest("34+57", CarbonInteger(91))
        exprTest("-34+57", CarbonInteger(23))
        exprTest("1+1+2", CarbonInteger(4))
    }

    @Test
    fun orderOfOperations() {
        exprTest("1+2*3", CarbonInteger(7))
        exprTest("2*3+1", CarbonInteger(7))
    }

    @Test
    fun equals() {
        exprTest("1+2 == 5", CarbonBoolean(false))
        exprTest("1+2 == 3", CarbonBoolean(true))
        exprTest("1+2 == 1", CarbonBoolean(false))
    }

    @Test
    fun lessThan() {
        exprTest("1+2 < 5", CarbonBoolean(true))
        exprTest("1+2 < 3", CarbonBoolean(false))
        exprTest("1+2 < 1", CarbonBoolean(false))
    }

    @Test
    fun lessThanOrEqual() {
        exprTest("1+2 <= 5", CarbonBoolean(true))
        exprTest("1+2 <= 3", CarbonBoolean(true))
        exprTest("1+2 <= 1", CarbonBoolean(false))
    }

    @Test
    fun greaterThan() {
        exprTest("1+2 > 5", CarbonBoolean(false))
        exprTest("1+2 > 3", CarbonBoolean(false))
        exprTest("1+2 > 1", CarbonBoolean(true))
    }

    @Test
    fun greaterThanOrEqual() {
        exprTest("1+2 >= 5", CarbonBoolean(false))
        exprTest("1+2 >= 3", CarbonBoolean(true))
        exprTest("1+2 >= 1", CarbonBoolean(true))
    }
}