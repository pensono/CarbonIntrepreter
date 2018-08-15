package org.carbon.runtime

import org.carbon.CarbonException

/**
 * @author Ethan
 * @param <CET> CarbonExpression declaredType of the input
 * @param <CER> CrabonExpression declaredType of the output
 */
class WrappedOperatorExpression<out CET: CarbonExpression, out CER: CarbonExpression, T, R>(
        val base: CET,
        val operatorName: String,
        private val operator: (T, T) -> R,
        private val unwrapper: (CET) -> T,
        private val wrapper: (R) -> CER,
        type: CarbonExpression
    ) : CarbonExpression(type, formalParameters = listOf("lhs")) {

    override fun apply(arguments: List<CarbonExpression?>): CER {
        assert(arguments.size == 1)

        val wrappedLhs = arguments[0] as? CET ?: throw CarbonException("Parameter does not have the correct underlying declaredType")
        val rhs = unwrapper(base)
        val lhs = unwrapper(wrappedLhs)

        return wrapper(operator(rhs, lhs))
    }

    override fun getShortString(): String = "Operator $operatorName"
    override fun getBodyString(level: Int): String = ""
}

class OperatorExpression(
        val base: CarbonExpression,
        val operatorName: String,
        type: CarbonExpression,
        private val operator: (CarbonExpression, CarbonExpression) -> CarbonExpression
) : CarbonExpression(type, formalParameters = listOf("lhs")) {

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        assert(arguments.size == 1)

        return operator(base, arguments[0]!!)
    }

    override fun getShortString(): String = "Operator $operatorName"
    override fun getBodyString(level: Int): String = ""
}