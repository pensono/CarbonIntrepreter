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
        val evaluatedBase = base.link(scope).reduce()
        return evaluatedBase.getMember(memberName) ?: throw CompilationException("Member $memberName not found in $evaluatedBase", location)
    }

    // TODO is this ever used?
    //override fun lookupName(name: String): CarbonExpression? = base.lookupName(memberName)?.lookupName(name)

    // We don't needs types yet, so just ignore this
    //override val type = base.link().lookupName(memberName)!!.type

    override fun getShortString(): String = "Member Expression. Member name: $memberName. Base:"
    override fun getBodyString(level: Int): String = base.getFullString(level)
}
