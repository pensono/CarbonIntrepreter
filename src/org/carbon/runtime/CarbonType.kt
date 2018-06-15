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
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var type: CarbonType = INSTANCE
}