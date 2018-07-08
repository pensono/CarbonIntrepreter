package org.carbon.syntax

import org.antlr.v4.runtime.misc.Interval
import org.carbon.CompilationException
import org.carbon.fullString
import org.carbon.indented
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class AppliedNode(private val location: Interval, private val base: Node, private val actualParameters: List<Node?>) : Node(), CarbonAppliable {
    override fun eval(scope: CarbonScope): CarbonExpression {
        val evaluatedParameters = actualParameters.map { n -> n!!.eval(scope).eval_reduce(scope) } // Worry about partial application later

        return base.eval(scope).apply(evaluatedParameters)
    }

    // TODO base.type - actualParameters:type
    //override var type = base.type

//    override fun link(scope: CarbonScope): CarbonExpression {
//        val evaluatedBase = base.link(scope)
//        val evaluatedParameters = actualParameters.map { e -> e?.link(scope) }.requireNoNulls()
//
//        return evaluatedBase.apply(evaluatedParameters)
//    }

//    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
//        val newParameters = this.actualParameters.toMutableList()
//
//        for (parameter in actualParameters) {
//            val index = newParameters.indexOf(null)
//            if (index == -1) {
//                throw CompilationException("Too many arguments to second application of a partially applied function", location) // I don't think the right location will be returned here
//            }
//
//            newParameters[index] = parameter
//        }
//
//        return AppliedNode(location, base, newParameters)
//    }



    override fun getShortString(): String = "Applied Expression."
    override fun getBodyString(level: Int): String =
            indented(level, "Base:") + base.getFullString(level + 1) + "\n" +
            indented(level, "Actual Parameters:") + fullString(level + 1, actualParameters)

}

