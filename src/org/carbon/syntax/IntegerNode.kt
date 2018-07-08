package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class IntegerNode(val value: Int) : Node() {
    override fun eval(scope: CarbonScope): CarbonExpression = CarbonInteger(value)

//    override fun lookupName(memberName: String): CarbonExpression? =
//        when(memberName) {
//            "+" -> binaryOp(Int::plus)
//            "*" -> binaryOp(Int::times)
//            else -> super.lookupName(memberName)
//        }
// I think this code will be moved into CarbonInteger
//    private fun binaryOp(operation: (Int, Int) -> Int) : Node =
//        OperatorNode<IntegerNode>{ o -> IntegerNode(operation(o.value, value)) }

//    val type: CarbonType = IntegerType

    override fun equals(other: Any?): Boolean = (other is IntegerNode) && other.value == value
    override fun hashCode(): Int = value
    override fun getShortString(): String = "IntegerNode($value)"
}