package org.carbon

import org.carbon.runtime.CarbonBoolean
import org.carbon.syntax.wrapInteger
import org.carbon.syntax.wrapString
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

    @Test
    fun stringMutation() {
        mutTest("""
            Str = Reg("Hahaha")
            ChangeStr =
                Str := "Lol"
        """, "ChangeStr", "Str", wrapString("Lol"))
    }

    // Need Enumeration types for this
//    @Test
//    fun boolMutation() {
//        mutTest("""
//            Bool = Reg(True)
//            Change =
//                Bool := False
//        """, "Change", "Bool", CarbonBoolean(false))
//    }

    @Test
    fun sequenceOperator() {
        mutTest("""
            Num = Reg(4)
            ChangeNum =
                Num := 5;
                Num := 6
        """, "ChangeNum", "Num", wrapInteger(6))
    }


    @Test
    fun sequenceOperatorVars() {
        mutTest("""
            Num = Reg(4)
            Change5 = Num := 5
            Change6 = Num := 6
            Changes = Change5; Change6
        """, "Changes", "Num", wrapInteger(6))
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