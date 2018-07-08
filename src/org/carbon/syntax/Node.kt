package org.carbon.syntax

import org.carbon.CarbonException
import org.carbon.PrettyPrintable
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
abstract class Node: PrettyPrintable {
    abstract fun eval(scope: CarbonScope): CarbonExpression

    override fun toString() = getFullString()
}