package org.carbon

import org.carbon.runtime.CarbonInteger
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Ethan Shea
 * *
 * @date 6/13/2018
 */
class IntegerTests {
    @Test
    fun numerals() {
        exprTest("1", CarbonInteger(1))
        exprTest("34", CarbonInteger(34))
        exprTest("137", CarbonInteger(137))
        exprTest("-1", CarbonInteger(-1))
        exprTest("-34", CarbonInteger(-34))
        exprTest("-137", CarbonInteger(-137))
        // TODO Decimal numbers
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
}