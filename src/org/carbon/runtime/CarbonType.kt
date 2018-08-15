package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
object CarbonType  : CarbonExpression(type_ = null) { // I have to do this because I cant pass the object CarbonType to the CarbonExpression constructor
    override fun getShortString(): String = "CarbonType"
}

/**
 * Used for filling in holes until the declaredType system is fully developed.
 */
object RandomType : CarbonExpression(type_ = CarbonType)