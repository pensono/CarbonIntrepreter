package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/17/2018
 */
class IdentifierExpression(private val scope: CarbonScope, private val name: String) : CarbonExpression() {
    override fun getMember(name: String): CarbonExpression? = scope.getMember(this.name)?.getMember(name)
    override fun eval(): CarbonExpression = scope.getMember(name)!!.eval() // TODO Better error handling if the name is not found
}