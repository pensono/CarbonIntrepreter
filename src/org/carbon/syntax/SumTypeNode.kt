package org.carbon.syntax

import org.carbon.runtime.*

class SumTypeNode(val options: Map<String, Node?>) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression {
        val linkedOptions = options.mapValues { option ->
            option.value?.link(scope) ?: CarbonExpression(CarbonType, memberCallback = ::simpleOptionMembers) // Blank expression for enum options with no content. (Like True)
        }

        return CarbonExpression(CarbonType, actualParameters = linkedOptions)
    }

    override fun getShortString(): String = "Sum Type Node. Options:"
    //override fun getBodyString(level: Int): String = fullString(level + 1, options)
}

private fun simpleOptionMembers(expr: CarbonExpression) = mapOf(
        "==" to OperatorExpression(expr, "==", RandomType) { rhs, lhs ->
            CarbonBoolean(rhs == lhs)
        }
)