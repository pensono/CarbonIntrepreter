package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class CarbonRootExpression : CarbonExpression() { // Refactor this into a CarbonScope? // Refactor this to be immutable?
    val members: MutableMap<String, CarbonExpression> = mutableMapOf("Integer" to IntegerType)

    override fun getMember(name: String): CarbonExpression? = members[name]

    fun putMember(name: String, member: CarbonExpression) = members.put(name, member)

    override var type: CarbonType
        get() = throw UnsupportedOperationException()
        set(value) { }
}