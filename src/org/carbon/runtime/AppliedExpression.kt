package org.carbon.runtime

import org.antlr.v4.runtime.misc.Interval
import org.carbon.CompilationException
import org.carbon.fullString
import org.carbon.indented

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class AppliedExpression(private val location: Interval, private val base: CarbonExpression, private val actualParameters: List<CarbonExpression?>) : CarbonExpression(), CarbonAppliable {
    // TODO base.type - actualParameters:type
    //override var type = base.type

    override fun eval(scope: CarbonScope): CarbonExpression {
        if (actualParameters.contains(null)) {
            return this // No reduction possible
            // throw CompilationException("Can't evaluate a function which is not fully applied.\n$this", location)
        }

        val evaluatedBase = base.eval(scope) as CarbonAppliable // TODO proper error handling. for when the base can't be applied. May be this could just be made more generic so the cast isn't necessary?
        val evaluatedParameters = actualParameters.requireNoNulls().map { e -> e.eval(scope) }

        return evaluatedBase.apply(evaluatedParameters).eval(scope)
    }

    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
        val newParameters = this.actualParameters.toMutableList()

        for (parameter in actualParameters) {
            val index = newParameters.indexOf(null)
            if (index == -1) {
                throw CompilationException("Too many arguments to second application of a partially applied function", location) // I don't think the right location will be returned here
            }

            newParameters[index] = parameter
        }

        return AppliedExpression(location, base, newParameters)
    }

    override fun getShortString(): String = "Applied Expression."
    override fun getBodyString(level: Int): String =
            indented(level, "Base:") + base.getFullString(level + 1) + "\n" +
            indented(level, "Actual Parameters:") + fullString(level + 1, actualParameters)

}

