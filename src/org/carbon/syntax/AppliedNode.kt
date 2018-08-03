package org.carbon.syntax

import org.antlr.v4.runtime.misc.Interval
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

        return baseExpr.apply(evaluatedParameters).eval()
    }

    override fun getShortString(): String = "Applied Node."
    override fun getBodyString(level: Int): String =
            indented(level, "Base:") + base.getFullString(level + 1) + "\n" +
            indented(level, "Actual Parameters:") + fullString(level + 1, actualParameters)
}
