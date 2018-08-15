package org.carbon.runtime

class FunctionType(val formalTypes: List<CarbonExpression>, val resultType: CarbonExpression) : CarbonExpression(CarbonType)

fun magmaType(set : CarbonExpression) = FunctionType(listOf(set, set), set)