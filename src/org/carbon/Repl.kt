package org.carbon

import org.antlr.v4.runtime.CharStreams
import org.carbon.runtime.RootScope

/**
 * @author Ethan Shea
 * @date 6/12/2018
 */
fun main(args: Array<String>) {
    compile(CharStreams.fromFileName("res/Dataflow.cbn"), RootScope())
}