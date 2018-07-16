package org.carbon.runtime

import org.carbon.syntax.Node

/**
 * @author Ethan
 */
class CompositeExpression(
        val lexicalScope: CarbonScope,
        val members: Map<String, CarbonExpression>,
        val derivedMembers: Map<String, Node>,
        val formalParameters : List<String> = listOf<String>() // String/type?
        //val actualParameters : Map<String, CarbonExpression> = mapOf()
    ) : CarbonExpression(){

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

        return CompositeExpression(lexicalScope, newMembers, derivedMembers, newFormalParameters)
        //return CompositeExpression(lexicalScope, newMembers, newFormalParameters)
    }

    // TODO check for colissions between members and actualParameters
    override fun lookupName(name: String): CarbonExpression? =
        getMember(name) ?: lexicalScope.lookupName(name)
}