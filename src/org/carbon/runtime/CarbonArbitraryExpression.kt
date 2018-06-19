package org.carbon.runtime

/**
 * @author Ethan
 */
open class CarbonArbitraryExpression(val type: CarbonType, private val members: Map<String, CarbonExpression>) : CarbonExpression() {
    override fun getMember(name: String): CarbonExpression? = members[name]
}