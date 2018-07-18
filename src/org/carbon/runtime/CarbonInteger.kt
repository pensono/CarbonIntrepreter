package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonInteger(var value: Int) : CarbonExpression(operatorCallback = ::generateOperators) {
    override fun getShortString(): String = "CarbonInteger($value)"

    // Can this override be removed?
    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        assert(arguments.isEmpty())
        return this
    }

    // Mostly here for tests
    override fun equals(other: Any?): Boolean = (other is CarbonInteger) && other.value == value
    override fun hashCode(): Int = value
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