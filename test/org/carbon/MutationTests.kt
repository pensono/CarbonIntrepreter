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
            reg Num = 4
            ChangeNum = // Monads :D
                Num := 5
        """, "ChangeNum", "Num", wrapInteger(5))
    }

    @Test
    fun stringMutation() {
        mutTest("""
            reg Str = "Hahaha"
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
            reg Num = 4
            ChangeNum =
                Num := 5;
                Num := 6
        """, "ChangeNum", "Num", wrapInteger(6))
    }


    @Test
    fun sequenceOperatorVars() {
        mutTest("""
            reg Num = 4
            Change5 = Num := 5
            Change6 = Num := 6
            Changes = Change5; Change6
        """, "Changes", "Num", wrapInteger(6))
    }

    @Test
    fun indirectlyObserveMutation() {
        mutTest("""
            reg Num = 4
            OtherNum = Num + 2
            ChangeNum =
                Num := 5
        """, "ChangeNum", "OtherNum", wrapInteger(7))
    }

    @Test
    fun mutateAndOperation() {
        mutTest("""
            reg Num = 4
            ChangeNum = // Monads :D
                Num := 5
            OtherNum = Num + 1
        """, "ChangeNum", "OtherNum", wrapInteger(6))
    }

    @Test
    fun mutateMember() {
        mutTest("""
            reg Obj = {reg Num = 4}()
            ChangeNum =
                Obj.Num := 5
            R = Obj.Num
        """, "ChangeNum", "R", wrapInteger(5))
    }
}