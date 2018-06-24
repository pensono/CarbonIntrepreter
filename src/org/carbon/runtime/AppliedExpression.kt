package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class AppliedExpression(private val base: CarbonExpression, private val actualParameters: List<CarbonExpression>) : CarbonExpression() {
    // TODO base.type - actualParameters:type
    //override var type = base.type

    override fun eval(scope: CarbonScope): CarbonExpression {
        val evaluatedBase = base.eval(scope) as CarbonAppliable // TODO proper error handling. for when the base can't be applied. May be this could just be made more generic so the cast isn't necessary?
        val evaluatedParameters = actualParameters.map { e -> e.eval(scope) }

        return evaluatedBase.apply(evaluatedParameters).eval(scope)
    }
}

