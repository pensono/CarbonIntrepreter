package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class CarbonRootExpression : CarbonExpression() {
    override fun apply(exp: CarbonExpression): CarbonExpression {
        throw UnsupportedOperationException("not implemented")
    }

    val members: MutableMap<String, CarbonExpression> = mutableMapOf()

    override fun getMember(name: String): CarbonExpression? = members[name]

    fun putMember(name: String, member: CarbonExpression) = members.put(name, member)

    override var type: CarbonType
        get() = throw UnsupportedOperationException()
        set(value) { }
}