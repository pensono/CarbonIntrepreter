package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class AppliedExpression(private val base: CarbonExpression, private val actualParameters: List<CarbonExpression>) : CarbonExpression() {
    // TODO base.type - actualParameters:type
    override var type = base.type

    override fun getMember(name: String): CarbonExpression {
        return base.type
    }

    override fun eval(): CarbonExpression {
        val evaluatedParameters = actualParameters.map(CarbonExpression::eval)
        val evaluatedBase = base.eval() as CarbonType // TODO proper error handling

        return evaluatedBase.apply(evaluatedParameters)
    }
}

// Might be a way this function can be generic
fun operatorExpression(type: CarbonType, fn :(CarbonExpression) -> CarbonExpression): CarbonExpression {
    return object : CarbonType() {
        override var type: CarbonType = type

        override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
            assert(actualParameters.size == 1, {"operator must accept exactly one argument"}) // TODO proper error handling
            return fn(actualParameters[0].eval()) // I don't think eval should be happening here
        }
    }
}