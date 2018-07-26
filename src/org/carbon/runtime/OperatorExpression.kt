package org.carbon.runtime

import org.carbon.CarbonException

/**
 * @author Ethan
 * @param <CET> CarbonExpression type of the input
 * @param <CER> CrabonExpression type of the output
 */
class OperatorExpression<out CET: CarbonExpression, out CER: CarbonExpression, T, R>(
        val base: CET,
        val operatorName: String,
        private val operator: (T, T) -> R,
        private val unwrapper: (CET) -> T,
        private val wrapper: (R) -> CER
    ) : CarbonExpression(formalParameters = listOf("lhs")) {

    override fun apply(arguments: List<CarbonExpression?>): CER {
        assert(arguments.size == 1)

        val wrappedLhs = arguments[0] as? CET ?: throw CarbonException("Parameter does not have the correct underlying type")
        val lhs = unwrapper(wrappedLhs)
        val rhs = unwrapper(base)

        return wrapper(operator(rhs, lhs))
    }

    override fun getShortString(): String = "Operator $operatorName"
    override fun getBodyString(level: Int): String = ""
}
