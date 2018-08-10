package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

class BranchNode(private val predicate: Node, private val trueBranch: Node, private val falseBranch: Node) : Node() {
    override fun link(scope: CarbonScope): CarbonExpression {
        val result = predicate.link(scope).eval()
        val takeBranch = result == scope.lookupName("True") // Is this the right way to do it? Maybe it's also a good idea to use Carbon's ==
        val branch = if (takeBranch) trueBranch else falseBranch

        return branch.link(scope).eval()
    }

    override fun getShortString(): String = "Branch Node"
}