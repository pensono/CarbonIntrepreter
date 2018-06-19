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
        val env = testEnv("A=3;")
        Assert.assertEquals(CarbonInteger(3), env.getMember("A"))
    }

    @Test
    fun identifierOperator() {
        val env = testEnv("A=3;B=A+2;")
        Assert.assertEquals(CarbonInteger(5), env.getMember("B")!!.eval())
    }

    @Test
    fun outOfOrderDependencies() {
        val env = testEnv("""
            C = A + B;
            A = 1;
            B = 2;
        """)

        Assert.assertEquals(CarbonInteger(3), env.getMember("C")!!.eval())
    }

    @Test
    fun dotNotation() {
        // A little strange to do it with an infix operator, but it should work anyways
        exprTest("1.+(1)", CarbonInteger(2))
        exprTest("34.+(57)", CarbonInteger(91))
        exprTest("-34.+(57)", CarbonInteger(23))
    }

}