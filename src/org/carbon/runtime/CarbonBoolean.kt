package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonBoolean(value: Boolean) : CarbonPrimitive<Boolean>(BooleanType, value, { expr -> generateOperators(expr as CarbonBoolean)}) {
    override fun getShortString(): String {
        val valueStr = if (value) "True" else "False"
        return "CarbonBoolean($valueStr)"
    }
}

private fun generateOperators(expr: CarbonBoolean) = mapOf(
        "&&" to booleanMagma(expr, "&&", Boolean::and),
        "||" to booleanMagma(expr, "&&", Boolean::or) // xor?
)

private fun booleanMagma(base: CarbonBoolean, opName: String, operation: (Boolean, Boolean) -> Boolean) =
        WrappedOperatorExpression(base, opName, operation, CarbonBoolean::value, ::CarbonBoolean, BooleanType, BooleanType)

object BooleanType : CarbonExpression(CarbonType) {
    override fun getShortString(): String = "Boolean Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
//        if (arguments.size != 1) // TODO something more robust (maybe a declaredType system?)
//            throw CompilationException("Not the correct number of arguments for creating a string")

        val parameter = arguments[0] as CarbonBoolean // TODO throw if not a string (use a declaredType system?)
        return CarbonBoolean(parameter.value)
    }
}