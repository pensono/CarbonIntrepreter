package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class CarbonInteger(val value: Int) : CarbonExpression() {
    override fun getMember(name: String): CarbonExpression? =
        when(name) {
            "+" -> binaryOp(Int::plus)
            "*" -> binaryOp(Int::times)
            else -> super.getMember(name)
        }

    private fun binaryOp(operation: (Int, Int) -> Int) : CarbonExpression =
        OperatorExpression<CarbonInteger>(IntegerType) { o -> CarbonInteger(operation(o.value, value)) }

    val type: CarbonType = IntegerType

    override fun equals(other: Any?): Boolean = (other is CarbonInteger) && other.value == value
    override fun hashCode(): Int = value
    override fun getShortString(): String = "CarbonInteger($value)"
}

object IntegerType : CarbonType() {
    override fun getShortString(): String = "IntegerType"
}