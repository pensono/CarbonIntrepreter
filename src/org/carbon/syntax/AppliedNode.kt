package org.carbon.syntax

import org.antlr.v4.runtime.misc.Interval
import org.carbon.CarbonTypeException
import org.carbon.fullString
import org.carbon.indented
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class AppliedNode(private val location: Interval, private val base: Node, private val actualParameters: List<Node?>) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression {
        val baseExpr = base.link(scope)
        val evaluatedParameters = actualParameters.map { n -> n?.link(scope) }

        if (baseExpr.formalParameters.size != actualParameters.size) {
            throw CarbonTypeException("Incorrect number of parameters to $base")
        } else {
            for (pair in evaluatedParameters.zip(baseExpr.formalTypes)) {
                if (pair.first != null && !pair.first!!.type.subtypeOf(pair.second)) {
                    throw CarbonTypeException("${pair.first} is not a subtype of ${pair.second}!", location)
                }
            }
        }

        return baseExpr.apply(evaluatedParameters).eval() // TODO get rid of this eval
    }

    override fun getShortString(): String = "Applied Node."
    override fun getBodyString(level: Int): String =
            indented(level, "Base:") + base.getFullString(level + 1) + "\n" +
            indented(level, "Actual Parameters:") + fullString(level + 1, actualParameters)
}
