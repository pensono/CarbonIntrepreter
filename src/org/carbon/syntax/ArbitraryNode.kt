package org.carbon.syntax

import org.carbon.fullString
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan
 */
//open class ArbitraryNode(private val lexicalScope: CarbonScope,
//                         //val type: CarbonType,
//                         override val members: Map<String, Node>) : Node() { // May need to be Node
//    override fun link(scope: CarbonScope): CarbonExpression {
//        // This mechanism of changing the scope seems very strange.
//        // The scope is first established when ArbitraryTypeNode.apply is called, but that scope is replaced when
//        // link is called. This scope is correct because it's a part of a member expression
//        TODO("Not converted")
//        //return ArbitraryNode(scope, type, members)
//    }
//
//    override fun lookupName(name: String): CarbonExpression? = members[name] ?: lexicalScope.lookupName(name)
//
//    override fun getShortString(): String = "Arbitrary Expression. Members:"
//    override fun getBodyString(level: Int): String = fullString(level + 1, members)
//}