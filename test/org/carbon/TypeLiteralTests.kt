package org.carbon

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonType
import org.carbon.runtime.IntegerType
import org.junit.Assert
import org.junit.Test

/**
 * @author Ethan
 */
class TypeLiteralTests {
    @Test
    fun basicTypeLiteral() {
        val expr = testEval("{A:Integer}") as CarbonType
        Assert.assertEquals(expr.getInstanceMember("A"),  IntegerType)
    }

    @Test
    fun implicitlyNamedMember() {
        val expr = testEval("{Integer}") as CarbonType
        Assert.assertEquals(expr.getInstanceMember("Integer"),  IntegerType)
    }

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
}