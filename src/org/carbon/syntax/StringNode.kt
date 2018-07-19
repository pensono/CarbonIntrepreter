package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonScope
import org.carbon.runtime.CarbonString

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class StringNode(val value: String) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression = CarbonString(value)

    override fun getShortString(): String = "StringNode($value)"
}