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

    /**
     * Returns the result of applying this expression (with some reduction?)
     */
    open fun apply(actualParameters: List<CarbonExpression>): CarbonExpression { // Should this be in CarbonType? or some CarbonFunction like class. CarbonAppliable?
        TODO("not implemented")
    }

    open val type: CarbonType = INSTANCE
}