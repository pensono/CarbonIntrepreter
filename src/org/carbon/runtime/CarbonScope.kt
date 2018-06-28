package org.carbon.runtime

/**
 * @author Ethan
 */
abstract class CarbonScope: PrettyPrintable {
    // Some debate in me about making the dot operator infix...
    open fun getMember(name: String): CarbonExpression? = null

    /**
     * Not commutative
     */
    operator fun plus(o: CarbonScope) = CombinedScope(this, o)

    override fun toString(): String = getFullString()
}

// Moving scope out of eval would prevent the need for this class
class CombinedScope(private val superScope: CarbonScope, private val subScope: CarbonScope) : CarbonScope() {
    override fun getMember(name: String): CarbonExpression? {
        return subScope.getMember(name) ?: superScope.getMember(name)
    }

    override fun getShortString(): String = "Combined Scope. Super/sub scope"
    override fun getBodyString(level: Int): String =
            superScope.getFullString(level) + "\n" + subScope.getFullString(level)
}

class LazyScope(var target:CarbonScope? = null) : CarbonScope() {
    override fun getMember(name: String): CarbonExpression? =
        target?.getMember(name) ?: super.getMember(name)

    override fun getShortString(): String = "Lazy scope. Target:"
    override fun getBodyString(level: Int): String =
            target?.getFullString(level + 1) ?: indented(level + 1, "None.")
}