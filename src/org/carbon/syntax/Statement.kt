package org.carbon.syntax

import org.carbon.PrettyPrintable

/**
 * @author Ethan
 */

class Statement(val name: String, val body: Node, val formalParameters: List<String>) : PrettyPrintable {
    override fun getShortString(): String = "Statement $name(${formalParameters.joinToString(",")}). Body:"
    override fun getBodyString(level: Int): String = body.getBodyString(level + 1)
}