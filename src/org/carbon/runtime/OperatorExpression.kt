package org.carbon.runtime

/**
 * @author Ethan
 */
// Might be a way this function can be generic
class OperatorExpression(type: CarbonType, private val fn :(CarbonExpression) -> CarbonExpression): CarbonExpression(), CarbonAppliable {
    val type: CarbonType = type

    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
        assert(actualParameters.size == 1, {"operator must accept exactly one argument"}) // TODO proper error handling
        return fn(actualParameters[0])
    }
}