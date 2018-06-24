package org.carbon.runtime

import org.antlr.v4.runtime.misc.Interval

/**
 * @author Ethan Shea
 * @date 6/17/2018
 */
class IdentifierExpression(private val location: Interval, private val name: String) : CarbonExpression() {
    //override fun getMember(name: String): CarbonExpression? = scope.getMember(this.name)?.getMember(name)
    override fun eval(scope: CarbonScope): CarbonExpression {
        val expr = scope.getMember(name) ?: throw CompilationException("Identifier $name not found in scope $scope", location)
        return expr.eval(scope)
    }
}