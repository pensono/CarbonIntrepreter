package org.carbon.runtime

import org.carbon.indented


// Does this make sense as a subclass?
class CarbonRegister(initialValue: CarbonExpression, type: CarbonExpression) : CarbonExpression(type, body = initialValue.body, memberCallback = ::registerOperations) {
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

private fun registerOperations(expr: CarbonExpression) = mapOf( // CarbonType here should actually be a polymorphic type A in A -> Monad
        ":=" to OperatorExpression(expr, "assignment", CarbonType, MonadType) { lhs, rhs -> AssignmentMonad(lhs as CarbonRegister, rhs)}
)