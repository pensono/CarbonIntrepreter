package org.carbon.runtime

abstract class CarbonMonad() : CarbonExpression(MonadType, memberCallback = ::monadOperators) {
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
        ";" to OperatorExpression(expr, "bind", MonadType, MonadType) { lhs, rhs -> CompositeMonad(lhs as CarbonMonad, rhs as CarbonMonad)}
)

object MonadType : CarbonExpression(CarbonType)