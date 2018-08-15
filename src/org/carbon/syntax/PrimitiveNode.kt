package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

abstract class PrimitiveNode<T:Any>(val value: T, val type: CarbonExpression, val operators: (CarbonExpression) -> Map<String, CarbonExpression>) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression = toExpr()
    fun toExpr() = CarbonExpression(type, body = this, memberCallback = operators)

    //override fun inferredType(scope: CarbonScope): CarbonExpression = type

    override fun getShortString(): String = "${this.javaClass.simpleName}($value)"

    override fun equals(other: Any?): Boolean = other?.javaClass == this.javaClass && (other as PrimitiveNode<T>).value == value
    override fun hashCode(): Int = value.hashCode()
}

fun <T, Node: PrimitiveNode<T>> unwrapPrimitive(expr: CarbonExpression) : T = (expr.body!! as Node).value
fun <T, Node: PrimitiveNode<T>> wrapPrimitive(constructor: (T) -> Node) = { value: T -> constructor(value).toExpr() } // Curried