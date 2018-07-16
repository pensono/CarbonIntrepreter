package org.carbon

import org.carbon.runtime.CarbonInteger
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
            Point = { X:Integer, Y:Integer };
            Point3D = Point : { Z: Integer }; // Horrible example of subclassing
            P = Point3D(1,2,3);
            R = P.Z;
        """, "R", CarbonInteger(3))
    }

    // TODO test squash between Integer and an arbitrary type

    @Test
    fun applicationSingleArg() {
        exprTest("{A:Integer}(2).A", CarbonInteger(2))
    }

    @Test
    fun applicationMultipleArg() {
        exprTest("{A:Integer,B:Integer}(2,3).A", CarbonInteger(2))
        exprTest("{A:Integer,B:Integer}(2,3).B", CarbonInteger(3))
    }

    @Test
    fun derivedMember() {
        envTest("""
            Rectangle = {
                Width:Integer,
                Height:Integer,
                Area = Width * Height,
            };
            R = Rectangle(3,4).Area;
        """, "R", CarbonInteger(12))
    }

    @Test
    fun derivedMemberUsesOuterScope() {
        envTest("""
            Factor = 4; // Random constant
            Rectangle = {
                Width:Integer,
                Height:Integer,
                SuperArea = Width * Height * Factor,
            };
            R = Rectangle(3,4).SuperArea;
        """, "R", CarbonInteger(48))
    }

    // TODO check names being used twice
}