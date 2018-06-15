package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
class CarbonInteger(val value: Int) : CarbonExpression() {
    override fun getMember(name: String): CarbonExpression? {
        return null
    }

    override var type: CarbonType = IntegerType

    override fun toString(): String = "CarbonInteger($value)"

    override fun equals(other: Any?): Boolean = (other is CarbonInteger) && other.value == value
}

object IntegerType : CarbonType() {

}