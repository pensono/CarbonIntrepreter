package org.carbon.runtime

import org.carbon.CompilationException

/**
 * @author Ethan
 */
class CarbonInteger(var value: Int) : CarbonExpression() {
    override fun getShortString(): String = "CarbonInteger($value)"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        assert(arguments.isEmpty())
        return this
    }

//    override val formalParameters: List<String>
//        get() = TODO("not implemented")
    override fun getMember(name: String) = members[name]
    val members: Map<String, CarbonExpression> = mapOf(
            "+" to integerMagma("+", Int::plus),
            "*" to integerMagma("*", Int::times)
    )

    private fun integerMagma(opName: String, operation :(Int, Int) -> Int) =
        OperatorExpression(this, opName, CarbonInteger::value, ::CarbonInteger, operation)

    // Mostly here for tests
    override fun equals(other: Any?): Boolean = (other is CarbonInteger) && other.value == value
    override fun hashCode(): Int = value
}

object IntegerType : CarbonExpression() {
    override fun getShortString(): String = "Integer Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
//        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
//            throw CompilationException("Not the correct number of arguments for creating an int")

        val parameter = arguments[0] as CarbonInteger // TODO throw if not an int (use a type system?)
        return CarbonInteger(parameter.value)
    }
}