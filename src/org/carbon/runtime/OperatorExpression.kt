package org.carbon.runtime

import org.carbon.CarbonException

/**
 * @author Ethan
 * @param <CE> CarbonExpression
 */
class OperatorExpression<out CE: CarbonExpression, T>(
        val base: CE,
        val operatorName: String,
        private val operator: (T, T) -> T,
        private val wrapper: (T) -> CE,
        private val unwrapper: (CE) -> T
    ) : CarbonExpression() {

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        val wrappedLhs = arguments[0] as? CE ?: throw CarbonException("Parameter does not have the correct underlying type")
        val lhs = unwrapper(wrappedLhs)
        val rhs = unwrapper(base)

        return wrapper(operator(rhs, lhs))
    }

    override fun getShortString(): String = "Operator $operatorName"
}
