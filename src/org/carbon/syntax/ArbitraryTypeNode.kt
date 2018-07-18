package org.carbon.syntax

import org.carbon.fullString
import org.carbon.runtime.*

/**
 * @author Ethan
 */
class ArbitraryTypeNode(
        private val instanceMembers: List<Pair<String, Node>>, // Name to type
        private val derivedMembers: Map<String, Node> // Name to body
    ) : Node() {

    override fun link(scope: CarbonScope): CarbonExpression {
        // Is this scope correct?
        val parameterNames = instanceMembers.map { p -> p.first }

        return CarbonExpression(scope, null, derivedMembers, formalParameters = parameterNames, operatorCallback = { expr ->
            mapOf(":" to OperatorExpression(expr, "squash",
                    { lhs, rhs ->
                        CarbonExpression(
                                scope,
                                null, // What should body be?
                                lhs.derivedMembers + rhs.derivedMembers,
                                lhs.actualParameters + rhs.actualParameters,
                                lhs.formalParameters + rhs.formalParameters)
                    }, { it }, { it }))
        })
    }

    override fun getShortString(): String = "ArbitraryTypeNode. Instance actualParameters:"
    override fun getBodyString(level: Int): String =
            fullString(level + 1, instanceMembers.toMap()) + fullString(level + 1, derivedMembers.toMap())
}