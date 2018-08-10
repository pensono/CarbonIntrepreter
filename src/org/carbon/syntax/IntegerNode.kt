package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope
import org.carbon.runtime.WrappedOperatorExpression

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class IntegerNode(value: Int) : PrimitiveNode<Int>(value ,::integerOperators)

private fun integerOperators(scope: CarbonScope) =
    { expr: CarbonExpression -> mapOf(
        "+" to integerMagma(expr, scope, "+", Int::plus),
        "-" to integerMagma(expr, scope, "-", Int::minus),
        "*" to integerMagma(expr, scope, "*", Int::times),
        "==" to integerRelation(expr, scope, "==", Int::equals),
        "<" to integerRelation(expr, scope, "<", { x, y -> x < y}),
        ">" to integerRelation(expr, scope, ">", { x, y -> x > y}),
        "<=" to integerRelation(expr, scope, "<=", { x, y -> x <= y}),
        ">=" to integerRelation(expr, scope, ">=", { x, y -> x >= y})
        )
    }

private fun integerMagma(base: CarbonExpression, scope: CarbonScope, opName: String, operation: (Int, Int) -> Int) : CarbonExpression =
        WrappedOperatorExpression(base, opName, operation, ::unwrapPrimitive, wrapInteger(scope))

private fun integerRelation(base: CarbonExpression, scope: CarbonScope, opName: String, operation: (Int, Int) -> Boolean) =
        WrappedOperatorExpression(base, opName, operation, ::unwrapPrimitive, wrapBoolean(scope))

val wrapInteger = {scope : CarbonScope -> wrapPrimitive(scope, ::IntegerNode)}
fun wrapBoolean(input: Boolean) = {scope: CarbonScope -> if (input) scope.lookupName("True") else scope.lookupName("False") } // Might not always look up Boolean.True or Boolean.False