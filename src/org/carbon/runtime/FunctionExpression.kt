package org.carbon.runtime

/**
 * @author Ethan
 */
// Eventually should also include parameter types
class FunctionExpression(private val lexicalScope: CarbonScope,
                         private val parameterNames: List<String>,
                         private val body: CarbonExpression) : CarbonExpression(), CarbonAppliable {
    // Eval should error?

    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
        return AppliedFunctionExpression(lexicalScope, parameterNames.zip(actualParameters), body)
    }

    override fun getShortString(): String = "Function Expression. Parameter Names: " + parameterNames.joinToString(", ") + ". Body:"
    override fun getBodyString(level: Int): String = body.getFullString(level + 1)
}

class AppliedFunctionExpression(private val lexicalScope: CarbonScope,
                                private val actualParameters: List<Pair<String, CarbonExpression>>,
                                private val body: CarbonExpression) : CarbonExpression() {
    override fun eval(scope: CarbonScope): CarbonExpression =
        body.eval(FunctionScope(actualParameters.toMap(), lexicalScope))

    override fun getShortString(): String = "Applied Function."
    override fun getBodyString(level: Int): String = body.getFullString(level + 1)
}

// Moving scope out of eval would prevent the need for this class
// TODO can this be merged with Scope::+?
private class FunctionScope(private val parameters: Map<String, CarbonExpression>, private val superScope: CarbonScope) : CarbonScope() {
    override fun getShortString(): String = "Function Scope. Body string not implemented"

    override fun getMember(name: String): CarbonExpression? {
        return parameters[name] ?: superScope.getMember(name)
    }
}
