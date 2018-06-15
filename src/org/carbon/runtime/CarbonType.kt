package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
open class CarbonType : CarbonExpression() {
    override fun apply(exp: CarbonExpression): CarbonExpression {
        throw UnsupportedOperationException("not implemented")
    }

    companion object {
        val INSTANCE : CarbonType = CarbonType()
    }

    override fun getMember(name: String): CarbonExpression {
        throw UnsupportedOperationException("not implemented")
    }

    override var type: CarbonType = INSTANCE
}