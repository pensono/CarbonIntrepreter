package org.carbon

import org.carbon.runtime.CarbonInteger
import org.carbon.syntax.wrapInteger
import org.junit.Assert
import org.junit.Test

/**
 * @author Ethan
 */
class MutationTests {
    @Test
    fun basicMutation() {
        mutTest("""
            Num = Reg(4)
            ChangeNum = // Monads :D
                Num := 5
        """, "ChangeNum", "Num", wrapInteger(5))
    }

//    @Test
//    fun mutateAndOperation() {
//        mutTest("""
//            Num = Reg(4)
//            ChangeNum = // Monads :D
//                Num := 5
//            OtherNum = Num + 1
//        """, "ChangeNum", "OtherNum", wrapInteger(6))
//    }
}