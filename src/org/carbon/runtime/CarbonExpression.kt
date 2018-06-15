package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
abstract class CarbonExpression {
    // Members. Maybe just a lookup function since members can be obtained through the type
    // Type
    abstract fun getMember(name: String) : CarbonExpression?
    abstract var type : CarbonType
}