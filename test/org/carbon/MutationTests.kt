package org.carbon

import org.carbon.runtime.CarbonInteger
import org.junit.Assert
import org.junit.Test

/**
 * @author Ethan
 */
class MutationTests {
    @Test
    fun derivedMemberUsesOuterScope() {
        mutTest("""
            Num := 4;
            *ChangeNum() {
                Num := 5
            }
        """, "ChangeNum", "Num", CarbonInteger(5))
    }
}