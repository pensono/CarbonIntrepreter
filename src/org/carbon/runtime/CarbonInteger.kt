package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class CarbonInteger(val value: Int) : CarbonExpression() {
    override fun apply(exp: CarbonExpression): CarbonExpression {
        throw UnsupportedOperationException("not implemented")
    }

    override fun getMember(name: String): CarbonExpression? =
        when(name) {
            "+" -> binaryOp(Int::plus)
            "*" -> binaryOp(Int::times)
            else -> super.getMember(name)
        }

    private fun binaryOp(operation: (Int, Int) -> Int) : CarbonExpression =
        operatorExpression(IntegerType) { o -> CarbonInteger(operation((o as CarbonInteger).value, value)) }


    override var type: CarbonType = IntegerType

    override fun toString(): String = "CarbonInteger($value)"

    override fun equals(other: Any?): Boolean = (other is CarbonInteger) && other.value == value
    override fun hashCode(): Int = value
}

object IntegerType : CarbonType() {

}