package org.carbon.syntax

import org.carbon.CarbonTypeException
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonRegister
import org.carbon.runtime.CarbonScope
import org.carbon.runtime.RandomType

/**
 * @author Ethan
 */
// Is this enum necessary? Maybe subclasses would be a better solution
class Statement(val isRegister: Boolean, val name: String, val formalParameters: List<Pair<String, Node>>, val declaredType: Node?, val body: Node) : Node() {
    override fun getShortString(): String = "Statement $name(${formalParameters.joinToString(",")}). Body:"
    override fun getBodyString(level: Int): String = body.getBodyString(level + 1)

    override fun link(scope: CarbonScope) : CarbonExpression {
        val linkedFormalParameters = formalParameters.map { pair -> pair.first to pair.second.link(scope) } // Correct scope? No idea. Might have to capture the scope in the formal parameter itself.

        val body = CarbonExpression(RandomType, scope, body, formalParameters = linkedFormalParameters)

        val declaredTypeExpr = declaredType?.link(scope)
        if (declaredTypeExpr != null && body.type.subtypeOf(declaredTypeExpr)) {
            throw CarbonTypeException("$body is not a subtype of $declaredTypeExpr")
        }

        return if (isRegister) {
             CarbonRegister(body.eval(), RandomType) // This eval may be too early. Also is it correct to pass the declaredType in twice?
        } else {
            body
        }
    }
}
