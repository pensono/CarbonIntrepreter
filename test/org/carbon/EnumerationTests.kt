package org.carbon

import org.carbon.runtime.CarbonBoolean
import org.junit.Test

/**
 * @author Ethan Shea
 *
 * @date 6/13/2018
 */
class EnumerationTests {
    @Test
    fun basicEnum() {
        envTest("""
            Opts = | A | B
            X = Opts.A
            Y = Opts.A
            R = X == Y
        """, "R", CarbonBoolean(true))
        // TODO add test case for false. (requires not equals operator)
    }

    // TODO tests for more complicated enumerations
}