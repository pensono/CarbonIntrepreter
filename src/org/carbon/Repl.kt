package org.carbon

import org.antlr.v4.runtime.CharStreams
import org.carbon.runtime.RootScope

/**
 * @author Ethan Shea
 * @date 6/12/2018
 */
fun main(args: Array<String>) {
    var environment = RootScope()
    // TODO load the standard library

    while (true) {
        print("> ")
        val input = readLine() ?: break

        val value = evaluate(input, environment)!!

        println(value.getFullString())
    }
}