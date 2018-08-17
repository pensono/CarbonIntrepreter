package org.carbon.syntax

import org.carbon.PrettyPrintable
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
abstract class Node: PrettyPrintable {
    abstract fun link(scope: CarbonScope): CarbonExpression
    open fun linkType(scope: CarbonScope): CarbonExpression = link(scope).type // Seems to be a good default. Little weird though because it means that the linking is done twice

    override fun toString() = getFullString()
}