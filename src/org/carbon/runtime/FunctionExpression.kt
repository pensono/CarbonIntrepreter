package org.carbon.runtime

/**
 * @author Ethan
 */
// Eventually should also include parameter types
class FunctionExpression(private val lexicalScope: CarbonScope, private val parameterNames: List<String>, private val body: CarbonExpression) : CarbonExpression(), CarbonAppliable {
    // Eval should error?

    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
        return AppliedFunctionExpression(lexicalScope, parameterNames.zip(actualParameters), body)
    }
}

class AppliedFunctionExpression(private val lexicalScope: CarbonScope, private val actualParameters: List<Pair<String, CarbonExpression>>, private val body: CarbonExpression) : CarbonExpression() {

    override fun eval(scope: CarbonScope): CarbonExpression =
        body.eval(FunctionScope(actualParameters.toMap(), lexicalScope))
}

// Moving scope out of eval would prevent the need for this class
// TODO can this be merged with Scope::+?
private class FunctionScope(private val parameters: Map<String, CarbonExpression>, private val superScope: CarbonScope) : CarbonScope() {
    override fun getMember(name: String): CarbonExpression? {
        return parameters[name] ?: superScope.getMember(name)
    }
}
