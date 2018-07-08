package org.carbon.runtime

import org.carbon.PrettyPrintable

/**
 * @author Ethan
 */
abstract class CarbonExpression: PrettyPrintable, CarbonScope() {
    open fun apply(arguments: List<CarbonExpression>) : CarbonExpression = this // Weak sauce implementation

    // TODO change name to avoid conflicting with Node::eval_reduce
    open fun eval_reduce(scope: CarbonScope) = this // Good default?

    // Set member?

    override fun getShortString(): String = "Carbon Expression"
    override fun toString(): String = getFullString()
}
