package org.carbon.runtime

import org.carbon.fullString

/**
 * @author Ethan
 */
open class CarbonArbitraryExpression(private val lexicalScope: CarbonScope,
                                     val type: CarbonType,
                                     private val members: Map<String, CarbonExpression>) : CarbonExpression() {
    override fun eval(scope: CarbonScope): CarbonExpression {
        // This mechanism of changing the scope seems very strange.
        // The scope is first established when CarbonArbitraryType.apply is called, but that scope is replaced when
        // eval is called. This scope is correct because it's a part of a member expression
        return CarbonArbitraryExpression(scope, type, members)
    }

    override fun getMember(name: String): CarbonExpression? = members[name] ?: lexicalScope.getMember(name)

    override fun getShortString(): String = "Arbitrary Expression. Members:"
    override fun getBodyString(level: Int): String = fullString(level + 1, members)
}