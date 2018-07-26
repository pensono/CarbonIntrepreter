package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
object CarbonType : CarbonExpression() {
    val type: CarbonType = this

    override fun getShortString(): String = "CarbonType"
}