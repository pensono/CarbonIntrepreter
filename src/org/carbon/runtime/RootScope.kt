package org.carbon.runtime

import org.antlr.v4.runtime.CharStreams
import org.carbon.compileExpression
import org.carbon.fullString
import java.io.File

/**
 * @author Ethan Shea
 * @date 6/14/2018
 */
// Refactor this into a CarbonScope?
// Refactor this to be immutable? It can also be an object if its immutable
class RootScope : CarbonScope() {
    val members: MutableMap<String, CarbonExpression> = mutableMapOf(
            "Integer" to IntegerType,
            "String" to StringType
    )

    init {
        // Probably a better way to iterate here
        File("stdlib").walkTopDown().filter { file -> file.extension == "cbn" }
                .forEach { file ->
            compileExpression(CharStreams.fromPath(file.toPath()), this)
        }
    }

    override fun getMember(name: String): CarbonExpression? = members[name]

    fun putMember(name: String, member: CarbonExpression) = members.put(name, member)

    override fun getShortString(): String = "Root Scope"
    override fun getBodyString(level: Int): String = fullString(level + 1, members)
}