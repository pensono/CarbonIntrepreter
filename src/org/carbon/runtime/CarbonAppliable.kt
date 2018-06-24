package org.carbon.runtime

/**
 * @author Ethan
 */
interface CarbonAppliable {

    /**
     * Returns the result of applying this expression (with some reduction?)
     */
    fun apply(actualParameters: List<CarbonExpression>): CarbonExpression // Should this be in CarbonExpression and default to this?
}