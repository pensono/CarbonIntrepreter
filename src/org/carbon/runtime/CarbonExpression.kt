package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
abstract class CarbonExpression {
    // Members. Maybe just a lookup function since members can be obtained through the type
    // Some debate in me about making the dot operator infix...
    open fun getMember(name: String) : CarbonExpression? = null
    abstract val type : CarbonType

    open fun eval(): CarbonExpression = this
    /**
     * Returns the result of applying this expression (with some reduction?)
     */
    open fun apply(exp: CarbonExpression) : CarbonExpression { // May not be the best way to do this
        throw UnsupportedOperationException("not implemented")
    }
}