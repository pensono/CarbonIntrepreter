package org.carbon.runtime

import org.carbon.CarbonException

/**
 * @author Ethan
 */
class OperatorExpression<out CE: CarbonExpression, T>(
        val base: CE,
        val operatorName : String,
        private val unwrapper: (CE) -> T,
        private val wrapper: (T) -> CE,
        private val operator: (T, T) -> T
    ) : CarbonExpression() {

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        val wrappedLhs = arguments[0] as? CE ?: throw CarbonException("Parameter does not have the correct underlying type")
        val lhs = unwrapper(wrappedLhs)
        val rhs = unwrapper(base)

        return wrapper(operator(rhs, lhs))
    }

    override fun getShortString(): String = "Operator $operatorName"
}
