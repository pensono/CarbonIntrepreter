package org.carbon.runtime

import org.antlr.v4.runtime.misc.Interval

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
// Should this inherit from CarbonExpression, or some kind of AstNode?
class MemberExpression(private val location: Interval, private val base: CarbonExpression, private val memberName: String) : CarbonExpression() {
    override fun eval(scope: CarbonScope): CarbonExpression {
        val evaluatedBase = base.eval(scope)
        val unevaluatedMember = evaluatedBase.getMember(memberName) ?: throw CompilationException("Member $memberName not found in $base", location)
        return unevaluatedMember.eval(evaluatedBase)
    }

    override fun getMember(name: String): CarbonExpression? = base.getMember(memberName)?.getMember(name)

    // We don't needs types yet, so just ignore this
    //override val type = base.eval().getMember(memberName)!!.type

    override fun getShortString(): String = "Member Expression. Member name: $memberName. Base:"
    override fun getBodyString(level: Int): String = base.getFullString(level + 1)
}