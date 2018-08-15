package org.carbon.runtime

import org.carbon.CompilationException

/**
 * @author Ethan
 */

object StringType : CarbonExpression(CarbonType) {
    override fun getShortString(): String = "String Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        if (arguments.size != 1) // TODO something more robust (maybe a declaredType system?)
            throw CompilationException("Not the correct number of arguments for creating a string")

        return arguments[0]!!
    }
}