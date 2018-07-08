package org.carbon.syntax

import org.carbon.runtime.CarbonExpression
import org.carbon.runtime.CarbonScope

/**
 * @author Ethan
 */
//class OperatorNode<T: Node>(private val fn :(T) -> T): Node(), CarbonAppliable {
//    override fun link(scope: CarbonScope): CarbonExpression {
//        TODO("not implemented")
//    }
//    //val type: CarbonType = type
//
//    override fun apply(actualParameters: List<CarbonExpression>): CarbonExpression {
//        assert(actualParameters.size == 1, {"operator must accept exactly one argument"}) // TODO proper error handling
//        return fn(actualParameters[0] as T)
//    }
//
//    override fun getShortString(): String = "Operator Expression"
//}