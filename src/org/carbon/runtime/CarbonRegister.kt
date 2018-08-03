package org.carbon.runtime

import org.carbon.CompilationException
import org.carbon.syntax.Node


// Does this make sense as a subclass?
class CarbonRegister(initialValue: Node) : CarbonExpression(body = initialValue, memberCallback = ::registerOperations) {
    fun update(newValue: Node) {
        body = newValue
    }

    override fun getShortString(): String = "Carbon Register"

    override fun eval(): CarbonExpression = this
}


object RegisterType : CarbonExpression() {
    override fun getShortString(): String = "Register Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
            throw CompilationException("Not the correct number of arguments for creating a register")

        val arg = arguments[0]

        return CarbonRegister(arguments[0]!!.body!!)
    }

}

private fun registerOperations(expr: CarbonExpression) = mapOf(
        ":=" to OperatorExpression(expr, "assignment") { lhs, rhs -> AssignmentMonad(lhs as CarbonRegister, rhs)}
)