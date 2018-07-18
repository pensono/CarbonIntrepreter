package org.carbon.runtime

import org.carbon.PrettyPrintable

/**
 * @author Ethan
 */
abstract class CarbonExpression: PrettyPrintable, CarbonScope() {
    /**
     * A null element signifies a parameter that was omitted. If any parameters are omitted, then a function
     * which takes the omitted parameters should be returned.
     */
    // TODO this logic of parameter omission should be consolidated to one location, rather than pushed upon the child classes
    open fun apply(arguments: List<CarbonExpression?>) : CarbonExpression = this // Weak sauce implementation

    open fun eval() = this // Good default?

    // Set member?

    override fun getShortString(): String = "Carbon Expression"
    override fun toString(): String = getFullString()
}
