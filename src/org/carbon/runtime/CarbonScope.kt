package org.carbon.runtime

import org.carbon.PrettyPrintable
import org.carbon.indented
import org.carbon.syntax.Node

/**
 * @author Ethan
 */
abstract class CarbonScope: PrettyPrintable {
    // Some debate in me about making the dot operator infix...
    open fun lookupName(name: String): CarbonExpression? = members[name]
    // Turn this into a map?
    open val members : Map<String, CarbonExpression> = mapOf()

    /**
     * Not commutative
     */
    operator fun plus(o: CarbonScope) = CombinedScope(this, o)

    /**
     * Not commutative
     */
    operator fun plus(o: Map<String, CarbonExpression>) = MapScope(this, o)

    override fun toString(): String = getFullString()
}

// Moving scope out of link would prevent the need for this class
class CombinedScope(private val superScope: CarbonScope, private val subScope: CarbonScope) : CarbonScope() {
    override fun lookupName(name: String): CarbonExpression? {
        return subScope.lookupName(name) ?: superScope.lookupName(name)
    }

    override fun getShortString(): String = "Combined Scope. Super/sub scope"
    override fun getBodyString(level: Int): String =
            superScope.getFullString(level) + "\n" + subScope.getFullString(level)
}

class LazyScope(var target: CarbonScope? = null) : CarbonScope() {
    override fun lookupName(name: String): CarbonExpression? =
        target?.lookupName(name)

    override fun getShortString(): String = "Lazy scope. Target:"
    override fun getBodyString(level: Int): String =
            target?.getFullString(level + 1) ?: indented(level + 1, "None.")
}

class EmptyScope() : CarbonScope() {
    override fun getShortString(): String = "Empty Scope"
    override fun lookupName(name: String): CarbonExpression? = null
}


// Moving scope out of link would prevent the need for this class
class MapScope(private val superScope: CarbonScope, private val parameters: Map<String, CarbonExpression>) : CarbonScope() {
    override fun getShortString(): String = "Function Scope. Body string not implemented"

    override fun lookupName(name: String): CarbonExpression? {
        return parameters[name] ?: superScope.lookupName(name)
    }
}
