package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
class AppliedExpression(private val base: CarbonExpression, private val actualParameters: List<CarbonExpression>) : CarbonExpression() {
    override fun apply(exp: CarbonExpression): CarbonExpression {
        throw UnsupportedOperationException("not implemented") // Add to actual parameters?
    }

    // TODO base.type - actualParameters:type
    override var type = base.type

    override fun getMember(name: String): CarbonExpression {
        return base.type
    }

    override fun eval(): CarbonExpression {
        return actualParameters.fold(base.eval(), {b: CarbonExpression, p: CarbonExpression -> b.apply(p)})
    }
}

// Might be a way this function can be generic
fun operatorExpression(type: CarbonType, fn :(CarbonExpression) -> CarbonExpression): CarbonExpression {
    return object : CarbonExpression() {
        override var type: CarbonType = type

        override fun apply(exp: CarbonExpression): CarbonExpression {
            return fn(exp.eval()) // I don't think eval should be happening here
        }
    }
}