package org.carbon.runtime

import org.carbon.fullString

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
// Refactor this into a CarbonScope?
// Refactor this to be immutable? It can also be an object if its immutable
class RootScope : CarbonScope() {
    val members: MutableMap<String, CarbonExpression> = mutableMapOf(
            "Reg" to RegisterType,
            "Integer" to IntegerType,
            "String" to StringType,
            "Boolean" to BooleanType,
            "True" to CarbonBoolean(true),
            "False" to CarbonBoolean(false)
    )

    override fun getMember(name: String): CarbonExpression? = members[name]

    fun putMember(name: String, member: CarbonExpression) = members.put(name, member)

    override fun getShortString(): String = "Root Scope"
    override fun getBodyString(level: Int): String = fullString(level + 1, members)
}