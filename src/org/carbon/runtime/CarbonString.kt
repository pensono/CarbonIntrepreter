package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonString(value: String) : CarbonPrimitive<String>(value, {expr -> generateOperators(expr as CarbonString)}) {
    override fun getShortString(): String = "CarbonString(\"$value\")"
}

private fun generateOperators(expr: CarbonString) = mapOf(
        "+" to stringMagma(expr, "+", String::plus) // Is there a way to remove the cast?
)

private fun stringMagma(base: CarbonString, opName: String, operation: (String, String) -> String) =
        OperatorExpression(base, opName, operation, CarbonString::value, ::CarbonString)

object StringType : CarbonExpression() {
    override fun getShortString(): String = "String Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
//        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
//            throw CompilationException("Not the correct number of arguments for creating a string")

        val parameter = arguments[0] as CarbonString // TODO throw if not a string (use a type system?)
        return CarbonString(parameter.value)
    }
}