package org.carbon.syntax

import org.antlr.v4.runtime.misc.Interval
import org.carbon.CompilationException
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/17/2018
 */
class IdentifierNode(private val location: Interval, private val name: String) : Node() {
    //override fun lookupName(name: String): Node? = scope.lookupName(this.name)?.lookupName(name)
    override fun eval(scope: CarbonScope): CarbonExpression =
            scope.lookupName(name) ?: throw CompilationException("Identifier $name not found in scope:\n$scope", location)

    override fun getShortString(): String = "Identifier Expression. Name: $name"
}