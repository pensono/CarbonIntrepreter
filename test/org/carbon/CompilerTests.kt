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

    @Test
    fun basicFunction() {
        envTest("Double(Integer) = Integer * 2; R=Double(4);", "R", CarbonInteger(8))
        envTest("Double(Integer) = Integer * 2; R=Double(4)+1;", "R", CarbonInteger(9))
        envTest("Double(Integer) = Integer * 2; R=1+Double(4);", "R", CarbonInteger(9))
    }

    @Test
    fun functionCallsFunction() {
        envTest("""
            Double(Integer) = Integer * 2;
            Quadruple(Integer) = 2 * Double(Integer);
            R=Quadruple(4);
        """, "R", CarbonInteger(16))
    }

    @Test
    fun lexicallyScoped() {
        envTest("""
            C = 5;
            F(C:Integer) = C + G(C);
            G(D:Integer) = D + C;
            R = F(2);
        """, "R", CarbonInteger(2+2+5)) // Will be 2 + 2 + 2 under dynamic scoping
    }
}