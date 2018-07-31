package org.carbon.syntax

import org.carbon.PrettyPrintable

/**
 * @author Ethan
 */
// Is this enum necessary? Maybe subclasses would be a better solution
class Statement(val name: String, val body: Node, val formalParameters: List<String>, val kind: StatementType) : PrettyPrintable {
    override fun getShortString(): String = "Statement $name(${formalParameters.joinToString(",")}). Body:"
    override fun getBodyString(level: Int): String = body.getBodyString(level + 1)
}

enum class StatementType {
    DECLARATION, INITIALIZATION
}