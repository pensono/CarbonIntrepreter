package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
open class CarbonType : CarbonExpression() {
    companion object {
        val INSTANCE : CarbonType = CarbonType()
    }

    open fun getInstanceMember(name: String): CarbonType? = null

    open val type: CarbonType = INSTANCE

    override fun getShortString(): String = "CarbonType"
}