package org.carbon.runtime

import org.carbon.syntax.Node

/**
 * @author Ethan
 */
class CompositeExpression(
        val lexicalScope: CarbonScope,
        val body: Node? = null, // Not sure if this is the best type, but we'll stick with it for now
        val derivedMembers: Map<String, Node> = mapOf(),
        val actualParameters: Map<String, CarbonExpression> = mapOf(),
        val formalParameters: List<String> = listOf<String>(),
        operatorCallback: (CompositeExpression) -> Map<String, CarbonExpression> = { _ -> mapOf() }
    ) : CarbonExpression(){

    val members = actualParameters + operatorCallback(this)

    // I don't think link should happen here
    override fun getMember(name: String): CarbonExpression? = members[name] ?: derivedMembers[name]?.link(this)

//    val derivedMemberExpressions = derivedMembers.mapValues { e -> e.value.link(this) }

// TODO clean up, this is similar to ScalarExpression.apply
    override fun apply(arguments: List<CarbonExpression?>) : CarbonExpression {
        assert(arguments.size == formalParameters.size) // You can't leave out parameters. Plus(4) does not compile, Plus(4,) does

        val nameMapping = formalParameters.zip(arguments)
        val newFormalParameters = nameMapping.filter { p -> p.second == null }
                .map { p -> p.first }
        val newMembers = nameMapping.toMap().filterValues { n -> n != null }
                .mapValues { e -> e.value as CarbonExpression } + members // Cast needed to get the type system to recognize that nulls were filtered out.

        // Eval with no formal parameters?
        return CompositeExpression(lexicalScope, body, derivedMembers, newMembers, newFormalParameters)
        //return CompositeExpression(lexicalScope, newMembers, newFormalParameters)
    }

    override fun eval() =
        // This seems wrong but it works
        if (formalParameters.isEmpty() && body != null) {
            body.link(this).eval() // It's weird how this line also appears in apply
        } else {
            this // Basically don't evaluate until fully applied
        }

    // TODO check for colissions between actualParameters and actualParameters
    override fun lookupName(name: String): CarbonExpression? =
        getMember(name) ?: lexicalScope.lookupName(name)
}