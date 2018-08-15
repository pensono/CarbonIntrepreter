package org.carbon.runtime

import java.util.*

/**
 * @author Ethan
 */
open class CarbonPrimitive<T>(
        type: CarbonExpression,
        var value: T,
        operatorCallback: (CarbonExpression) -> Map<String, CarbonExpression>
    ) : CarbonExpression(type, memberCallback = operatorCallback) {

    // Can this override be removed?
    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        assert(arguments.isEmpty())
        return this
    }

    override fun equals(other: Any?): Boolean = other is CarbonPrimitive<*> && Objects.equals(other.value, this.value) // Objects.equals to make nulls be equal
    override fun hashCode(): Int = (value ?: 0).hashCode() xor this.javaClass.hashCode()
}