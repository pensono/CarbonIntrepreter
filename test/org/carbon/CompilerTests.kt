package org.carbon

import org.carbon.runtime.CarbonInteger
import org.junit.Assert
import org.junit.Test

/**
 * @author Ethan
 */
class CompilerTests {
    @Test
    fun basicDefinition() {
        envTest("A=3;", "A", CarbonInteger(3))
    }

    @Test
    fun identifierOperator() {
        envTest("A=3;B=A+2;", "B", CarbonInteger(5))
    }

    @Test
    fun outOfOrderDependencies() {
        envTest("""
            C = A + B;
            A = 1;
            B = 2;
        """, "C", CarbonInteger(3))
    }

    @Test
    fun dotNotation() {
        // A little strange to do it with an infix operator, but it should work anyways
        exprTest("1.+(1)", CarbonInteger(2))
        exprTest("34.+(57)", CarbonInteger(91))
        exprTest("-34.+(57)", CarbonInteger(23))
    }

//    @Test
//    fun nestedScopes() {
//        envTest("""
//            E = 5;
//            A = {
//                C = E,
//                B = {
//                    D = C,
//                }
//            };
//            R = A().B.D;
//        """, "R", CarbonInteger(5))
//    }
}