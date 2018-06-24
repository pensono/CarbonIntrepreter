package org.carbon.runtime

/**
 * @author Ethan
 */
class OperatorExpression<T: CarbonExpression>(type: CarbonType, private val fn :(T) -> T): CarbonExpression(), CarbonAppliable {
    val type: CarbonType = type

    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
        assert(actualParameters.size == 1, {"operator must accept exactly one argument"}) // TODO proper error handling
        return fn(actualParameters[0] as T)
    }
}