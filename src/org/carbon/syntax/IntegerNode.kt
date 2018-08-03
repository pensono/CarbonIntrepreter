package org.carbon.syntax

import org.carbon.runtime.*

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class IntegerNode(val value: Int) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression = toExpr()
    fun toExpr() = CarbonExpression(body = this, memberCallback = ::generateOperators)

    override fun getShortString(): String = "IntegerNode($value)"

    override fun equals(other: Any?): Boolean = other is IntegerNode && other.value == value
    override fun hashCode(): Int = value
}


private fun generateOperators(expr: CarbonExpression) = mapOf(
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
        WrappedOperatorExpression(base, opName, operation, ::unwrapInteger, ::wrapInteger)

private fun integerRelation(base: CarbonExpression, opName: String, operation: (Int, Int) -> Boolean) =
        WrappedOperatorExpression(base, opName, operation, ::unwrapInteger, ::CarbonBoolean)

fun unwrapInteger(expr: CarbonExpression) : Int = (expr.body!! as IntegerNode).value
fun wrapInteger(value: Int) : CarbonExpression = IntegerNode(value).toExpr()
