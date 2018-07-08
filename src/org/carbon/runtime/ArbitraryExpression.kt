package org.carbon.runtime

import org.carbon.syntax.Node

/**
 * @author Ethan
 */
class ArbitraryExpression(
        val lexicalScope: CarbonScope,
        val body : Node,
        val formalParameters : List<String> = listOf<String>(), // String/type?
        val actualParameters : Map<String, CarbonExpression> = mapOf()
    ): CarbonExpression() {

    override fun apply(arguments: List<CarbonExpression>) : CarbonExpression {
        val newActualParameters = formalParameters.zip(arguments).toMap() + actualParameters
        val newFormalParameters = formalParameters.drop(newActualParameters.size)

        // I think this scoping is wrong
        return ArbitraryExpression(lexicalScope, body, newFormalParameters, newActualParameters)
    }

    override fun lookupName(name: String): CarbonExpression? = (lexicalScope + actualParameters).lookupName(name)

    override fun eval_reduce(scope: CarbonScope) = body.eval(scope + this).eval_reduce(scope + this)

    override fun getShortString(): String = "Arbitrary Expression. Body:"
    override fun getBodyString(level: Int): String = body.getBodyString(level + 1)
}