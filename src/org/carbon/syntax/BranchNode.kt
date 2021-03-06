package org.carbon.syntax

import org.carbon.runtime.CarbonBoolean
import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

class BranchNode(private val predicate: Node, private val trueBranch: Node, private val falseBranch: Node) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression {
        val result = predicate.link(scope).eval() as CarbonBoolean
        val branch = if (result.value) trueBranch else falseBranch

        return branch.link(scope).eval()
    }

    override fun getShortString(): String = "Branch Node"
}