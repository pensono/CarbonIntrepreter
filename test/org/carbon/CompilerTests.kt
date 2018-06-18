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
    fun outOfOrderDependencies() {
        val env = testEnv("""
            C = A + B;
            A = 1;
            B = 2;
        """)

        Assert.assertEquals(CarbonInteger(3), env.getMember("C"))
    }
}