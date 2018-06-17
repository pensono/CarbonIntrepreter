package org.carbon

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

//    @Test
//    fun implicitlyNamedMember() {
//        val expr = testEval("{Integer}") as CarbonType
//        Assert.assertEquals(expr.getInstanceMember("Integer"),  IntegerType)
//    }
}