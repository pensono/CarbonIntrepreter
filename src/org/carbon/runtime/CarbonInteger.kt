package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonInteger(value: Int) : CarbonPrimitive<Int>(value, {expr -> generateOperators(expr as CarbonInteger)}) {
    override fun getShortString(): String = "CarbonInteger($value)"
}

private fun generateOperators(expr: CarbonInteger) = mapOf(
        "+" to integerMagma(expr, "+", Int::plus),
        "-" to integerMagma(expr, "-", Int::minus),
        "*" to integerMagma(expr, "*", Int::times),
        "==" to integerRelation(expr, "==", Int::equals),
        "<" to integerRelation(expr, "<", {x, y -> x < y}),
        ">" to integerRelation(expr, ">", {x, y -> x > y}),
        "<=" to integerRelation(expr, "<=", {x, y -> x <= y}),
        ">=" to integerRelation(expr, ">=", {x, y -> x >= y})
)

private fun integerMagma(base: CarbonInteger, opName: String, operation: (Int, Int) -> Int) =
        OperatorExpression(base, opName, operation, CarbonInteger::value, ::CarbonInteger)

private fun integerRelation(base: CarbonInteger, opName: String, operation: (Int, Int) -> Boolean) =
        OperatorExpression(base, opName, operation, CarbonInteger::value, ::CarbonBoolean)

object IntegerType : CarbonExpression() {
    override fun getShortString(): String = "Integer Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
//        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
//            throw CompilationException("Not the correct number of arguments for creating an int")

        val parameter = arguments[0] as CarbonInteger // TODO throw if not an int (use a type system?)
        return CarbonInteger(parameter.value)
    }
}