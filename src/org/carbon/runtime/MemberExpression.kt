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
        return evaluatedBase.getMember(memberName) ?: throw CompilationException("Member $memberName not found in $base", location)
    }

    override fun getMember(name: String): CarbonExpression? = base.getMember(memberName)?.getMember(name)

    // We don't needs types yet, so just ignore this
    //override val type = base.eval().getMember(memberName)!!.type
}