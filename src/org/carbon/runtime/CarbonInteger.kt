package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonInteger(value: Int) : CarbonPrimitive<Int>(value, ::generateOperators) {
    override fun getShortString(): String = "CarbonInteger($value)"
}

private fun generateOperators(expr: CarbonExpression) = mapOf(
        "+" to integerMagma(expr as CarbonInteger, "+", Int::plus), // Is there a way to remove the cast?
        "*" to integerMagma(expr as CarbonInteger, "*", Int::times)
)

private fun integerMagma(base: CarbonInteger, opName: String, operation: (Int, Int) -> Int) =
        OperatorExpression(base, opName, operation, ::CarbonInteger, CarbonInteger::value)

object IntegerType : CarbonExpression() {
    override fun getShortString(): String = "Integer Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
//        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
//            throw CompilationException("Not the correct number of arguments for creating an int")

        val parameter = arguments[0] as CarbonInteger // TODO throw if not an int (use a type system?)
        return CarbonInteger(parameter.value)
    }
}