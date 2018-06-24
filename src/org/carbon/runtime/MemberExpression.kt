package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
// Should this inherit from CarbonExpression, or some kind of AstNode?
class MemberExpression(private val base: CarbonExpression, private val memberName: String) : CarbonExpression() {

    override fun eval(scope: CarbonScope): CarbonExpression = base.eval(scope).getMember(memberName)!! // TODO error message if member is not present

    override fun getMember(name: String): CarbonExpression? = base.getMember(memberName)?.getMember(name)

    // We don't needs types yet, so just ignore this
    //override val type = base.eval().getMember(memberName)!!.type
}