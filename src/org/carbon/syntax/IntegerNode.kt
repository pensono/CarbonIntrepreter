package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class IntegerNode(val value: Int) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression = CarbonInteger(value)

    override fun equals(other: Any?): Boolean = (other is IntegerNode) && other.value == value
    override fun hashCode(): Int = value
    override fun getShortString(): String = "IntegerNode($value)"
}