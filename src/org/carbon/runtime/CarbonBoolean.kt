package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonBoolean(value: Boolean) : CarbonPrimitive<Boolean>(value, {expr -> generateOperators(expr as CarbonBoolean)}) {
    override fun getShortString(): String {
        val valueStr = if (value) "True" else "False"
        return "CarbonBoolean($valueStr)"
    }
}

private fun generateOperators(expr: CarbonBoolean) = mapOf(
        "&&" to booleanMagma(expr, "&&", Boolean::and) // Is there a way to remove the cast?
)

private fun booleanMagma(base: CarbonBoolean, opName: String, operation: (Boolean, Boolean) -> Boolean) =
        WrappedOperatorExpression(base, opName, operation, CarbonBoolean::value, ::CarbonBoolean)

object BooleanType : CarbonExpression() {
    override fun getShortString(): String = "Boolean Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
//        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
//            throw CompilationException("Not the correct number of arguments for creating a string")

        val parameter = arguments[0] as CarbonBoolean // TODO throw if not a string (use a type system?)
        return CarbonBoolean(parameter.value)
    }
}