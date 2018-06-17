package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
// Should this inherit from CarbonExpression, or some kind of AstNode?
class MemberExpression(private val base: CarbonExpression, private val memberName: String) : CarbonExpression() {
    override fun apply(exp: CarbonExpression): CarbonExpression {
        throw UnsupportedOperationException("not implemented") // This doesn't even make sense to have.
    }

    override fun eval(): CarbonExpression = base.eval().getMember(memberName)!! // TODO error message if member is not present

    override fun getMember(name: String): CarbonExpression? = base.getMember(memberName)?.getMember(name)

    override val type = base.getMember(memberName)!!.type
}