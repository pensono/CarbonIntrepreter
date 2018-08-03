package org.carbon.syntax

import org.carbon.runtime.*

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class IntegerNode(value: Int) : PrimitiveNode<Int>(value ,::integerOperators)

private fun integerOperators(expr: CarbonExpression) = mapOf(
        "+" to integerMagma(expr, "+", Int::plus),
        "-" to integerMagma(expr, "-", Int::minus),
        "*" to integerMagma(expr, "*", Int::times),
        "==" to integerRelation(expr, "==", Int::equals),
        "<" to integerRelation(expr, "<", {x, y -> x < y}),
        ">" to integerRelation(expr, ">", {x, y -> x > y}),
        "<=" to integerRelation(expr, "<=", {x, y -> x <= y}),
        ">=" to integerRelation(expr, ">=", {x, y -> x >= y})
)

private fun integerMagma(base: CarbonExpression, opName: String, operation: (Int, Int) -> Int) =
        WrappedOperatorExpression(base, opName, operation, ::unwrapPrimitive, wrapInteger)

private fun integerRelation(base: CarbonExpression, opName: String, operation: (Int, Int) -> Boolean) =
        WrappedOperatorExpression(base, opName, operation, ::unwrapPrimitive, ::CarbonBoolean)

val wrapInteger = wrapPrimitive(::IntegerNode)