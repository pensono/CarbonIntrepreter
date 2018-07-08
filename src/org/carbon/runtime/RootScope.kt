package org.carbon.runtime

import org.carbon.fullString
import org.carbon.syntax.Node

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
// Refactor this into a CarbonScope?
// Refactor this to be immutable? It can also be an object if its immutable
class RootScope : CarbonScope() {
    override val members: MutableMap<String, CarbonExpression> = mutableMapOf("Integer" to IntegerType)

    fun putMember(name: String, member: CarbonExpression) = members.put(name, member)

    override fun getShortString(): String = "Root Scope"
    override fun getBodyString(level: Int): String = fullString(level + 1, members)
}