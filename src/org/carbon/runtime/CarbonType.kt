package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
open class CarbonType : CarbonExpression() {
    companion object {
        val INSTANCE : CarbonType = CarbonType()
    }

    override fun getMember(name: String): CarbonExpression {
        // : operator
        throw UnsupportedOperationException("not implemented")
    }

    open fun getInstanceMember(name: String): CarbonType? = null

    override var type: CarbonType = INSTANCE
}