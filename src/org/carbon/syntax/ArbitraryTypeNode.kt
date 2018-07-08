package org.carbon.syntax

import org.carbon.fullString
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan
 */
class ArbitraryTypeNode(private val lexicalScope: CarbonScope, private val instanceMembers: List<Pair<String, Node>>, private val derivedMembers: List<Pair<String, Node>>) : Node(), CarbonAppliable {
    override fun eval(scope: CarbonScope): CarbonExpression {
        TODO("not implemented")
    }

    // Pair<> or Parameter?
    // Store instance members as a less awkward type like a LinkedHashMap<>?
//    override fun getInstanceMember(name: String): CarbonType? =
//            instanceMembers.find { p -> p.first == name }?.second as CarbonType
//
//    override fun link(scope: CarbonScope): CarbonExpression {
//        TODO("Not implemented")
//        // return ArbitraryTypeNode(lexicalScope, evalInScope(instanceMembers, lexicalScope), derivedMembers) // Is lexical scope needed? I think just scope would suffice
//    }

//    private fun evalInScope(members: List<Pair<String, Node>>, scope: CarbonScope) =
//            members.map { p -> p.first to p.second.link(scope) }

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
//        val members = instanceMembers.zip(actualParameters) { i, p -> i.first to p}.toMap()
//
//        return ArbitraryNode(lexicalScope, this, members + derivedMembers)
//    }

    override fun getShortString(): String = "ArbitraryTypeNode. Instance members:"
    override fun getBodyString(level: Int): String =
            fullString(level + 1, instanceMembers.toMap()) + fullString(level + 1, derivedMembers.toMap())
}