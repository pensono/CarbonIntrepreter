package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan
 */
class ArbitraryInstanceNode(
        private val instanceMembers: List<Pair<String, Node>>, // Name to type
        val derivedMembers: List<Statement>
    ) : Node() {

    override fun link(scope: CarbonScope): CarbonExpression {
        val members = instanceMembers.associate { m -> m.first to scope.getMember(m.first)!! }
        return CarbonExpression(scope, actualParameters = members, memberCallback = {expr ->
            derivedMembers.associate { s -> s.name to s.link(expr) }
        })
    }

    override fun getShortString(): String = "Arbitrary Instance Node"
}