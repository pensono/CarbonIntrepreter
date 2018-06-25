package org.carbon.runtime

/**
 * @author Ethan
 */
abstract class CarbonScope {
    // Some debate in me about making the dot operator infix...
    open fun getMember(name: String): CarbonExpression? = null

    /**
     * Not commutative
     */
    operator fun plus(o: CarbonScope) = CombinedScope(this, o)
}

// Moving scope out of eval would prevent the need for this class
class CombinedScope(private val superScope: CarbonScope, private val subScope: CarbonScope) : CarbonScope() {
    override fun getMember(name: String): CarbonExpression? {
        return subScope.getMember(name) ?: superScope.getMember(name)
    }
}
