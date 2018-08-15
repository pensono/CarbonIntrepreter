package org.carbon.syntax

import org.carbon.fullString
import org.carbon.runtime.*

/**
 * @author Ethan
 */
class ArbitraryTypeNode(
        private val instanceMembers: List<Pair<String, Node>>, // Name to declaredType
        private val derivedMembers: List<Statement>
    ) : Node() {

    override fun link(scope: CarbonScope): CarbonExpression {
        // Is this scope correct?
        val parameterNames = instanceMembers.map { p -> p.first }

        return CarbonExpression(RandomType, scope, ArbitraryInstanceNode(instanceMembers, derivedMembers), formalParameters = parameterNames, memberCallback = { expr ->
            mapOf(":" to OperatorExpression(expr, "squash", type = RandomType) { lhs, rhs ->
                CarbonExpression(
                        RandomType,
                        scope,
                        null, // What should body be?
                        lhs.derivedMembers + rhs.derivedMembers,
                        lhs.actualParameters + rhs.actualParameters,
                        lhs.formalParameters + rhs.formalParameters)
            })
        })
    }

    override fun getShortString(): String = "ArbitraryTypeNode. Instance members:"
    override fun getBodyString(level: Int): String =
            fullString(level + 1, instanceMembers.toMap()) + fullString(level + 1, derivedMembers)
}