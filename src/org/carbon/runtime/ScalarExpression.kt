package org.carbon.runtime

import org.carbon.syntax.Node

/**
 * @author Ethan
 */
//class ScalarExpression(
//        val lexicalScope: CarbonScope,
//        val body : Node,
//        val formalParameters : List<String> = listOf<String>(), // String/type?
//        val actualParameters : Map<String, CarbonExpression> = mapOf()
//    ): CarbonExpression() {
//
//    override fun apply(arguments: List<CarbonExpression?>) : CarbonExpression {
//        assert(arguments.size == formalParameters.size) // You can't leave out parameters, holes should be null. Plus(4) does not compile, Plus(4,) does
//
//        val nameMapping = formalParameters.zip(arguments)
//        val newFormalParameters = nameMapping.filter { p -> p.second == null }
//                .map { p -> p.first }
//        val newActualParameters = nameMapping.toMap().filterValues { n -> n != null }
//                .mapValues { e -> e.value as CarbonExpression } + actualParameters // Cast needed to get the type system to recognize that nulls were filtered out.
//
//        if (newFormalParameters.isEmpty()) {
//            // Fully applied, actually evaluate the inside
//            return body.link(lexicalScope + newActualParameters)
//        } else {
//            return ScalarExpression(lexicalScope, body, newFormalParameters, newActualParameters)
//        }
//    }
//
//    override fun lookupName(name: String): CarbonExpression? = (lexicalScope + actualParameters).lookupName(name)
//
//    override fun eval() =
//        // This seems wrong but it works
//        if (formalParameters.isEmpty()) {
//            body.link(this).eval() // It's weird how this line also appears
//        } else {
//            this // Basically don't evaluate until fully applied
//        }
//
//    override fun getShortString(): String = "Scalar Expression. Body:"
//    override fun getBodyString(level: Int): String = body.getFullString(level)
//}