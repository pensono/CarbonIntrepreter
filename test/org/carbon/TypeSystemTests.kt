package org.carbon

import org.junit.Test

/**
 * @author Ethan Shea
 *
 * @date 6/13/2018
 */
class TypeSystemTests {
    @Test(expected = CarbonTypeException::class)
    fun basicAssignmentString() {
        testEnvEval("R: String = 4", "R")
    }

    @Test(expected = CarbonTypeException::class)
    fun basicAssignmentInt() {
        testEnvEval("R: Integer = \"lol\"", "R")
    }

    @Test(expected = CarbonTypeException::class)
    fun fromAppliedExpression() {
        testEnvEval("R: String = 4 + 2", "R")
    }

    @Test(expected = CarbonTypeException::class)
    fun inAppliedExpression() {
        testEnvEval("R = 4 + \"lol\"", "R")
    }

    // TODO Test basic assignment user defined types
    // TODO Test basic assignment enums
}