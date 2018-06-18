package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
abstract class CarbonExpression : CarbonScope() {
    abstract val type : CarbonType

    open fun eval(): CarbonExpression = this
}