package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonRegister
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan
 */
// Is this enum necessary? Maybe subclasses would be a better solution
class Statement(val name: String, val body: Node, val formalParameters: List<String>, val isRegister: Boolean) : Node() {
    override fun getShortString(): String = "Statement $name(${formalParameters.joinToString(",")}). Body:"
    override fun getBodyString(level: Int): String = body.getBodyString(level + 1)

    override fun link(scope: CarbonScope) : CarbonExpression {
        val body = CarbonExpression(scope, body, formalParameters = formalParameters)
        return if (isRegister) {
             CarbonRegister(body)
        } else {
            body
        }
    }
}
