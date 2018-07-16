package org.carbon.runtime

import org.carbon.syntax.Node

/**
 * @author Ethan
 */
//class ArbitraryType(
//        val lexicalScope: CarbonScope,
//        val instanceMembers: List<Pair<String, Node>>, // Name to type
//        val derivedMembers: List<Pair<String, Node>> // Name to body
//    ): CarbonType() {
//
//    override fun apply(arguments: List<CarbonExpression?>) : CarbonExpression {
//        assert(arguments.size == instanceMembers.size) // You can't leave out parameters. Plus(4) does not compile, Plus(4,) does
//
//        val newInstanceMembers = instanceMembers.map{m -> m.first}.zip(arguments).toMap() // Ignore the type for now
//
//        val newExpressionScope = LazyScope() // If there's a way to do this without the lazy scope, I'm all ears
//        val newDerivedMembers = derivedMembers.map{
//            m -> Pair(m.first, ScalarExpression(lexicalScope + newExpressionScope, m.second))
//        }.toMap()
//
//        //val expression = CompositeExpression(lexicalScope, newInstanceMembers + newDerivedMembers) // Just get it to compile. This class may be unnececary
//        val expression = CompositeExpression(lexicalScope, derivedMembers)
//        newExpressionScope.target = expression
//        return expression
//    }
//}