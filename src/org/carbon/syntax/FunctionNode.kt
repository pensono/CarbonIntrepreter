package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan
 */
// Eventually should also include parameter types
//class FunctionNode(private val lexicalScope: CarbonScope,
//                   private val parameterNames: List<String>,
//                   private val body: Node) : Node(), CarbonAppliable {
//    // Eval should error?
//
//    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
//        return AppliedFunctionExpression(lexicalScope, parameterNames.zip(actualParameters), body)
//    }
//
//    override fun getShortString(): String = "Function Expression. Parameter Names: " + parameterNames.joinToString(", ") + ". Body:"
//    override fun getBodyString(level: Int): String = body.getFullString(level + 1)
//}
//
//class AppliedFunctionExpression(private val lexicalScope: CarbonScope,
//                                private val actualParameters: List<Pair<String, CarbonExpression>>,
//                                private val body: Node) : Node() {
//    override fun link(scope: CarbonScope): CarbonExpression =
//        body.link(FunctionScope(actualParameters.toMap(), lexicalScope))
//
//    override fun getShortString(): String = "Applied Function."
//    override fun getBodyString(level: Int): String = body.getFullString(level + 1)
//}
