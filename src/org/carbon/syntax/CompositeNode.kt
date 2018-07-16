package org.carbon.syntax

import org.carbon.fullString
import org.carbon.runtime.ScalarExpression
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope
import org.carbon.runtime.CompositeExpression

/**
 * @author Ethan
 */
//open class CompositeNode(//private val lexicalScope: CarbonScope,
//                         //val type: CarbonType,
//                         val members: Map<String, Node>) : Node() { // May need to be Node
//    override fun link(scope: CarbonScope): CarbonExpression {
//        //return CompositeNode(scope, type, members)
//        val linkedMembers = members.mapValues { e -> e.value.link(scope) }
//        return CompositeExpression(scope, linkedMembers) // This doesn't seem right
//    }
//
////    override fun lookupName(name: String): CarbonExpression? = members[name] ?: lexicalScope.lookupName(name)
//
//    override fun getShortString(): String = "Arbitrary Expression. Members:"
//    override fun getBodyString(level: Int): String = fullString(level + 1, members)
//}