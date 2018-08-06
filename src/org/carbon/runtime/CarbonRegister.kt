package org.carbon.runtime

import org.carbon.CompilationException
import org.carbon.fullString
import org.carbon.indented


// Does this make sense as a subclass?
class CarbonRegister(initialValue: CarbonExpression) : CarbonExpression(body = initialValue.body, memberCallback = ::registerOperations) {
    var value = initialValue

    fun update(newValue: CarbonExpression) {
        value = newValue
        body = newValue.body // Is this the right thing to do?
    }

    override fun getMember(name: String): CarbonExpression? = super.getMember(name) ?: value.getMember(name)

    override fun getShortString(): String = "Carbon Register"
    override fun getBodyString(level: Int): String = indented(level, "Value:") + (value.getFullString(level + 1))

    override fun eval(): CarbonExpression = this

    override fun equals(other: Any?): Boolean = value == other
    override fun hashCode(): Int = value.hashCode() xor 0xCB1337
}

object RegisterType : CarbonExpression() {
    override fun getShortString(): String = "Register Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
            throw CompilationException("Not the correct number of arguments for creating a register")

        val arg = arguments[0]

        return CarbonRegister(arguments[0]!!)
    }

}

private fun registerOperations(expr: CarbonExpression) = mapOf(
        ":=" to OperatorExpression(expr, "assignment") { lhs, rhs -> AssignmentMonad(lhs as CarbonRegister, rhs)}
)