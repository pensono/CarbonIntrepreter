package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonArbitraryType(private val instanceMembers: List<Pair<String, CarbonExpression>>) : CarbonType(), CarbonAppliable { // Pair<> or Parameter?
    // Store instance members as a less awkward type like a LinkedHashMap<>?
    override fun getInstanceMember(name: String): CarbonType? =
            instanceMembers.find { p -> p.first == name }?.second as CarbonType

    override fun eval(scope: CarbonScope): CarbonExpression {
        return CarbonArbitraryType(instanceMembers.map{ p -> p.first to p.second.eval(scope)})
    }

    override fun getMember(name: String): CarbonExpression? =
        when(name) {
            // Not sure how I want to deal with name collisions yet.
            ":" -> OperatorExpression<CarbonArbitraryType>(CarbonType()) { o -> CarbonArbitraryType(instanceMembers + o.instanceMembers) }
            else -> super.getMember(name)
        }

    /**
     * Returns the result of applying this expression (with some reduction?)
     */
    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
        // TODO proper error handling
        assert(actualParameters.size == instanceMembers.size, {"Not the correct number of parameters. Expected: " + instanceMembers.size + " Actual: " + actualParameters.size})
        val members = instanceMembers.zip(actualParameters) { i, p -> i.first to p}.toMap()

        return CarbonArbitraryExpression(this, members)
    }
}