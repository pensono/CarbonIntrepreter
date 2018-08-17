package org.carbon

import org.carbon.runtime.CarbonBoolean
import org.carbon.syntax.wrapInteger
import org.junit.Test

/**
 * @author Ethan Shea
 * *
 * @date 6/13/2018
 */
class IntegerTests {
    @Test
    fun literals() {
        exprTest("1", wrapInteger(1))
        exprTest("34", wrapInteger(34))
        exprTest("137", wrapInteger(137))
        exprTest("-1", wrapInteger(-1))
        exprTest("-34", wrapInteger(-34))
        exprTest("-137", wrapInteger(-137))
        // TODO Decimal numbers
    }

    // Not sure I want to support this
//    @Test
//    fun expression() {
//        exprTest("Integer(1)", wrapInteger(1))
//        envTest("""
//            I = 3
//            R = Integer(I)
//        """, "R", wrapInteger(3))
//    }


    @Test
    fun addition() {
        exprTest("1+1", wrapInteger(2))
        exprTest("34+57", wrapInteger(91))
        exprTest("-34+57", wrapInteger(23))
        exprTest("1+1+2", wrapInteger(4))
    }

    @Test
    fun subtraction() {
        exprTest("1-1", wrapInteger(0))
        exprTest("1- 1", wrapInteger(0))
        exprTest("1 -1", wrapInteger(0))
        exprTest("1 - 1", wrapInteger(0))
        exprTest("34-57", wrapInteger(-23))
        exprTest("-34-57", wrapInteger(-91))
        exprTest("1-1-2", wrapInteger(-2))
    }

    @Test
    fun orderOfOperations() {
        exprTest("1+2*3", wrapInteger(7))
        exprTest("2*3+1", wrapInteger(7))
        exprTest("1-1-2*4", wrapInteger(-8))
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