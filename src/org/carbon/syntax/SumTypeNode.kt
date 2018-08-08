package org.carbon.syntax

import org.carbon.runtime.CarbonBoolean
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope
import org.carbon.runtime.OperatorExpression

class SumTypeNode(val options: Map<String, Node?>) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression {
        val linkedOptions = options.mapValues { option ->
            option.value?.link(scope) ?: CarbonExpression(memberCallback = ::simpleOptionMembers) // Blank expression for enum options with no content. (Like True)
        }

        return CarbonExpression(actualParameters = linkedOptions)
    }

    override fun getShortString(): String = "Sum Type Node. Options:"
    //override fun getBodyString(level: Int): String = fullString(level + 1, options)
}

private fun simpleOptionMembers(expr: CarbonExpression) = mapOf(
        "==" to OperatorExpression(expr, "==") { rhs, lhs ->
            CarbonBoolean(rhs == lhs)
        }
)