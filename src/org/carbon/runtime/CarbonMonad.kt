package org.carbon.runtime

abstract class CarbonMonad() : CarbonExpression(memberCallback = ::monadOperators) {
    abstract fun execute()
}

class CompositeMonad(val first: CarbonMonad, val second: CarbonMonad) : CarbonMonad() {
    override fun execute() {
        first.execute()
        second.execute()
    }
}

// Should rhs be Node?
class AssignmentMonad(val lhs: CarbonRegister, val rhs: CarbonExpression) : CarbonMonad(){
    override fun execute() {
        lhs.update(rhs)
    }
}

fun monadOperators(expr: CarbonExpression): Map<String, CarbonExpression> = mapOf(
        ";" to OperatorExpression(expr, "bind") { lhs, rhs -> CompositeMonad(lhs as CarbonMonad, rhs as CarbonMonad)}
)
