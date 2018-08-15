package org.carbon.runtime

import org.carbon.CompilationException

/**
 * @author Ethan
 */

object IntegerType : CarbonExpression(CarbonType) {
    override fun getShortString(): String = "Integer Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        if (arguments.size != 1) // TODO something more robust (maybe a declaredType system?)
            throw CompilationException("Not the correct number of arguments for creating an int")

//        val parameter = arguments[0] as CarbonInteger // TODO throw if not an int (use a declaredType system?)
//        return CarbonInteger(parameter.value)
        return arguments[0]!!
    }
}