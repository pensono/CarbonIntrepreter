package org.carbon.syntax

import org.antlr.v4.runtime.misc.Interval
import org.carbon.CompilationException
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
// Should this inherit from Node, or some kind of AstNode?
class MemberNode(private val location: Interval, private val base: Node, private val memberName: String) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression {
        val evaluatedBase = base.link(scope).eval()
        return evaluatedBase.getMember(memberName) ?: throw CompilationException("Member $memberName not found in $evaluatedBase", location)
    }

    override fun getShortString(): String = "Member Node. Member name: $memberName. Base:"
    override fun getBodyString(level: Int): String = base.getFullString(level)
}
