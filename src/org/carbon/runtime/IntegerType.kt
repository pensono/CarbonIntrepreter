package org.carbon.runtime

import org.carbon.CompilationException

/**
 * @author Ethan
 */

object IntegerType : CarbonExpression() {
    override fun getShortString(): String = "Integer Type"

    override fun apply(arguments: List<CarbonExpression?>): CarbonExpression {
        if (arguments.size != 1) // TODO something more robust (maybe a type system?)
            throw CompilationException("Not the correct number of arguments for creating an int")

//        val parameter = arguments[0] as CarbonInteger // TODO throw if not an int (use a type system?)
//        return CarbonInteger(parameter.value)
        return arguments[0]!!
    }
}