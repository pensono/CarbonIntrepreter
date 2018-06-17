package org.carbon.runtime

/**
 * @author Ethan
 */
class CarbonArbitraryType(private val instanceMembers: Map<String, CarbonType>) : CarbonType() {
    override fun getInstanceMember(name: String): CarbonType? = instanceMembers[name]

    // TODO apply. Generate a new arb expression with the given parameters
}