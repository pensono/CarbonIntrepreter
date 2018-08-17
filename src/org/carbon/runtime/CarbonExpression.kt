package org.carbon.runtime

import org.carbon.PrettyPrintable
import org.carbon.fullString
import org.carbon.indented
import org.carbon.syntax.Node

/**
 * @author Ethan
 */ // TODO clean up the IR from these arguments
open class CarbonExpression(
        type_: CarbonExpression?, // Should this be required? I'm starting to think no, but I'll finish the type system before making a determination
        val lexicalScope: CarbonScope? = null, // Not sure if this is the best declaredType, but we'll stick with it for now
        var body: Node? = null, // Should be non-nullable
        val derivedMembers: Map<String, CarbonExpression> = mapOf(),
        val actualParameters: Map<String, CarbonExpression> = mapOf(),
        val formalParameters: List<Pair<String, CarbonExpression>> = listOf(), // name to type
        memberCallback: (CarbonExpression) -> Map<String, CarbonExpression> = { _ -> mapOf() }
    ) : PrettyPrintable, CarbonScope() {

    val type = type_ ?: this // Sad hack because CarbonType can't pass itself in as it's own declaredType
    val formalTypes = formalParameters.map {p -> p.second}
    val members = actualParameters + memberCallback(this)

    // I don't think link should happen here
    override fun getMember(name: String): CarbonExpression? = members[name] ?: derivedMembers[name]

    /**
     * A null element signifies a parameter that was omitted. If any parameters are omitted, then a function
     * which takes the omitted parameters should be returned.
     *
     * Parameters should be fully evaluated before being passed into apply.
     */
    open fun apply(arguments: List<CarbonExpression?>) : CarbonExpression {
        assert(arguments.size == formalParameters.size) // You can't leave out parameters. Plus(4) does not compile, Plus(4,) does

        val nameMapping = formalParameters.zip(arguments)
        val newFormalParameters = nameMapping.filter { p -> p.second == null }
                .map { p -> p.first }
        val newMembers = nameMapping.toMap().filterValues { n -> n != null }
                .map { e -> e.key.first to e.value!! }.toMap() + members

        // Eval when no formal parameters?
        return CarbonExpression(type, lexicalScope, body, derivedMembers, newMembers, newFormalParameters)
    }

    open fun eval() : CarbonExpression =
        if (formalParameters.isEmpty() && body != null) {
             body!!.link(this)
        } else {
            this // Basically don't evaluate until fully applied
        }

    // TODO check for collisions between actualParameters and derivedMembers, or assert that keys are unique
    override fun lookupName(name: String): CarbonExpression? =
            getMember(name) ?: lexicalScope?.lookupName(name)

    open fun subtypeOf(expr: CarbonExpression) = expr == this

    override fun getShortString(): String = "Carbon Expression"
    override fun getBodyString(level: Int): String =
            indented(level, "Formal Parameters: " + formalParameters.joinToString(", ")) +
            indented(level, "Members:") + fullString(level + 1, members) + "\n" +
            indented(level, "Body:") + (body?.getFullString(level + 1) ?: indented(level + 1, "None."))
    override fun toString(): String = getFullString()

    override fun equals(other: Any?): Boolean = other is CarbonExpression && other.body == body
    override fun hashCode(): Int = body?.hashCode() ?: 0
}
