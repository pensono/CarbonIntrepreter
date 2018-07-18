package org.carbon.syntax

import org.carbon.fullString
import org.carbon.runtime.*

/**
 * @author Ethan
 */
class ArbitraryTypeNode(
        private val instanceMembers: List<Pair<String, Node>>, // Name to type
        private val derivedMembers: Map<String, Node> // Name to body
    ) : Node(), CarbonAppliable {

    override fun link(scope: CarbonScope): CarbonExpression {
        // Is this scope correct?
        val parameterNames = instanceMembers.map { p -> p.first }

        return CompositeExpression(scope, null, derivedMembers, formalParameters = parameterNames, operatorCallback = { expr ->
            mapOf(":" to OperatorExpression(expr, "squash",
                    { lhs, rhs -> CompositeExpression(
                            scope,
                            null, // What should body be?
                            lhs.derivedMembers + rhs.derivedMembers,
                            lhs.actualParameters + rhs.actualParameters,
                            lhs.formalParameters + rhs.formalParameters)
                    } ,{it},{it}))
        })

//        val newExpressionScope = LazyScope() // If there's a way to do this without the lazy scope, I'm all ears
//        val newDerivedMembers = derivedMembers.map{
//            m -> Pair(m.first, ScalarExpression(lexicalScope + newExpressionScope, m.second))
//        }.toMap()
//
//        val expression = CompositeExpression(lexicalScope, newDerivedMembers, parameterNames)
//        newExpressionScope.target = expression
//        return expression

        //return ScalarExpression(scope, body, parameterNames)

//        return ArbitraryType(lexicalScope, instanceMembers, derivedMembers)
    }

    // Pair<> or Parameter?
    // Store instance actualParameters as a less awkward type like a LinkedHashMap<>?
//    override fun getInstanceMember(name: String): CarbonType? =
//            instanceMembers.find { p -> p.first == name }?.second as CarbonType
//
//    override fun link(scope: CarbonScope): CarbonExpression {
//        TODO("Not implemented")
//        // return ArbitraryTypeNode(lexicalScope, evalInScope(instanceMembers, lexicalScope), derivedMembers) // Is lexical scope needed? I think just scope would suffice
//    }

//    private fun evalInScope(actualParameters: List<Pair<String, Node>>, scope: CarbonScope) =
//            actualParameters.map { p -> p.first to p.second.link(scope) }

//    override fun lookupName(name: String): CarbonExpression? =
//        when(name) {
//            // TODO Not sure how I want to deal with name collisions yet.
//            //":" -> OperatorNode<ArbitraryTypeNode>(CarbonType()) { o -> ArbitraryTypeNode(lexicalScope, instanceMembers + o.instanceMembers, derivedMembers + o.derivedMembers) }
//            else -> super.lookupName(name)
//        }
//
//    /**
//     * Returns the result of applying this expression (with some reduction?)
//     */
//    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
//        // TODO proper error handling
//        assert(actualParameters.size == instanceMembers.size, {"Not the correct number of parameters. Expected: " + instanceMembers.size + " Actual: " + actualParameters.size})
//        val actualParameters = instanceMembers.zip(actualParameters) { i, p -> i.first to p}.toMap()
//
//        return CompositeNode(lexicalScope, this, actualParameters + derivedMembers)
//    }

    override fun getShortString(): String = "ArbitraryTypeNode. Instance actualParameters:"
    override fun getBodyString(level: Int): String =
            fullString(level + 1, instanceMembers.toMap()) + fullString(level + 1, derivedMembers.toMap())
}