package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonArbitraryType(private val instanceMembers: List<Pair<String, CarbonExpression>>) : CarbonType() { // Pair<> or Parameter?
    // Store instance members as a less awkward type like a LinkedHashMap<>?
    override fun getInstanceMember(name: String): CarbonType? =
            instanceMembers.find { p -> p.first == name }?.second?.eval() as CarbonType // Should eval go here?

    /**
     * Returns the result of applying this expression (with some reduction?)
     */
    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
        // TODO proper error handling
        assert(actualParameters.size == instanceMembers.size, {"Not the correct number of parameters. Expected: " + instanceMembers.size + " Actual: " + actualParameters.size})
        val members = instanceMembers.zip(actualParameters) { i, p -> Pair(i.first, p)}.toMap()

        return CarbonArbitraryExpression(this, members)
    }
}