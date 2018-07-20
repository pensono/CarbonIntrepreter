package org.carbon.runtime

import org.carbon.PrettyPrintable
import org.carbon.fullString
import org.carbon.indented
import org.carbon.syntax.Node

/**
 * @author Ethan
 */
open class CarbonExpression(
        val lexicalScope: CarbonScope? = null, // Should this be required?
        val body: Node? = null, // Not sure if this is the best type, but we'll stick with it for now
        val derivedMembers: Map<String, CarbonExpression> = mapOf(),
        val actualParameters: Map<String, CarbonExpression> = mapOf(),
        val formalParameters: List<String> = listOf<String>(),
        memberCallback: (CarbonExpression) -> Map<String, CarbonExpression> = { _ -> mapOf() }
    ) : PrettyPrintable, CarbonScope() {

        val members = actualParameters + memberCallback(this)

        // I don't think link should happen here
        override fun getMember(name: String): CarbonExpression? = members[name] ?: derivedMembers[name]

        /**
         * A null element signifies a parameter that was omitted. If any parameters are omitted, then a function
         * which takes the omitted parameters should be returned.
         */
        open fun apply(arguments: List<CarbonExpression?>) : CarbonExpression {
            assert(arguments.size == formalParameters.size) // You can't leave out parameters. Plus(4) does not compile, Plus(4,) does

            val nameMapping = formalParameters.zip(arguments)
            val newFormalParameters = nameMapping.filter { p -> p.second == null }
                    .map { p -> p.first }
            val newMembers = nameMapping.toMap().filterValues { n -> n != null }
                    .mapValues { e -> e.value as CarbonExpression } + members // Cast needed to get the type system to recognize that nulls were filtered out.

            // Eval when no formal parameters?
            return CarbonExpression(lexicalScope, body, derivedMembers, newMembers, newFormalParameters)
        }

        open fun eval() : CarbonExpression =
                if (formalParameters.isEmpty() && body != null) {
                     body.link(this).eval()
                } else {
                    this // Basically don't evaluate until fully applied
                }

        // TODO check for collisions between actualParameters and derivedMembers, or assert that keys are unique
        override fun lookupName(name: String): CarbonExpression? =
                getMember(name) ?: lexicalScope?.lookupName(name)
    override fun getShortString(): String = "Carbon Expression"
    override fun getBodyString(level: Int): String =
            indented(level, "Formal Parameters: " + formalParameters.joinToString(", ")) +
            indented(level, "Members:") + fullString(level + 1, members) +
            indented(level, "Body:") + (body?.getFullString(level + 1) ?: indented(level + 1, "None."))
    override fun toString(): String = getFullString()
}
