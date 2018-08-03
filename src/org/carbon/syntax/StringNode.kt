package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.WrappedOperatorExpression

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class StringNode(value: String) : PrimitiveNode<String>(value, ::stringOperators)

private fun stringOperators(expr: CarbonExpression) = mapOf(
        "+" to stringMagma(expr, "+", String::plus) // Is there a way to remove the cast?
)

private fun stringMagma(base: CarbonExpression, opName: String, operation: (String, String) -> String) =
        WrappedOperatorExpression(base, opName, operation, ::unwrapPrimitive, wrapString)

val wrapString = wrapPrimitive(::StringNode)