package org.carbon

import org.carbon.syntax.wrapInteger
import org.junit.Assert
import org.junit.Test

/**
 * @author Ethan
 */
class TypeLiteralTests {
//    @Test
//    fun basicTypeLiteral() {
//        val expr = testEval("{A:Integer}") as CarbonType
//        Assert.assertEquals(expr.getInstanceMember("A"),  IntegerType)
//    }
//
//    @Test
//    fun implicitlyNamedMember() {
//        val expr = testEval("{Integer}") as CarbonType
//        Assert.assertEquals(expr.getInstanceMember("Integer"),  IntegerType)
//    }

    // TODO test that implicitly named parameters with refinements are named correctly ie, without the refinement attached
    // (Integer[> 0] is named Integer)

    @Test
    fun squashOperator() {
        envTest("""
            Point = { X:Integer, Y:Integer }
            Point3D = Point : { Z: Integer } // Horrible example of subclassing
            P = Point3D(1,2,3)
            R = P.Z
        """, "R", wrapInteger(3))
    }

    // TODO test squash between Integer and an arbitrary type

    @Test
    fun applicationSingleArg() {
        exprTest("{A:Integer}(2).A", wrapInteger(2))
    }

    @Test
    fun applicationMultipleArg() {
        exprTest("{A:Integer,B:Integer}(2,3).A", wrapInteger(2))
        exprTest("{A:Integer,B:Integer}(2,3).B", wrapInteger(3))
    }

    @Test(expected = CompilationException::class)
    fun squashOperatorRemoved() {
        testEval("{A:Integer}(2) : {B:Integer}(2)")
    }

    @Test
    fun derivedMember() {
        envTest("""
            Rectangle = {
                Width:Integer,
                Height:Integer,
                Area = Width * Height,
            }
            R = Rectangle(3,4).Area
        """, "R", wrapInteger(12))
    }

    @Test
    fun derivedMemberWithArguments() {
        envTest("""
            Rectangle = {
                Width:Integer,
                Height:Integer,
                SuperArea(Integer) = Width * Height * Integer
            }
            R = Rectangle(3,4).SuperArea(5)
        """, "R", wrapInteger(60))
    }

    @Test
    fun derivedMemberUsesOuterScope() {
        envTest("""
            Factor = 4 // Random constant
            Rectangle = {
                Width:Integer,
                Height:Integer,
                SuperArea = Width * Height * Factor,
            }
            R = Rectangle(3,4).SuperArea
        """, "R", wrapInteger(48))
    }

    // TODO check names being used twice
}