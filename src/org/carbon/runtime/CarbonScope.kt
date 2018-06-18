package org.carbon.runtime

/**
 * @author Ethan
 */
abstract class CarbonScope {
    // Some debate in me about making the dot operator infix...
    open fun getMember(name: String): CarbonExpression? = null
}