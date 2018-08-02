package org.carbon

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonString
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Ethan Shea
 * *
 * @date 6/13/2018
 */
class StringTests {
    @Test
    fun literals() {
        exprTest("\"Test\"", CarbonString("Test"))
        exprTest(" \"Test\"  ", CarbonString("Test"))
        exprTest("\"Test with spaces\"", CarbonString("Test with spaces"))
    }

    @Test
    fun newlines() {
        exprTest("\"\\n\"", CarbonString("\n"))
        exprTest("\"one\\ntwo\"", CarbonString("one\ntwo"))
    }

    @Test
    fun unicode() {
        exprTest("\"السلام عليكم\"", CarbonString("السلام عليكم"))
        exprTest("\"Dobrý den\"", CarbonString("Dobrý den"))
        exprTest("\"Hello\"", CarbonString("Hello"))
        exprTest("\"שָׁלוֹם\"", CarbonString("שָׁלוֹם"))
        exprTest("\"नमस्ते\"", CarbonString("नमस्ते"))
        exprTest("\"こんにちは\"", CarbonString("こんにちは"))
        exprTest("\"你好\"", CarbonString("你好"))
        exprTest("\"Olá\"", CarbonString("Olá"))
        exprTest("\"Здравствуйте\"", CarbonString("Здравствуйте"))
        exprTest("\"Hola\"", CarbonString("Hola"))
    }

    @Test
    fun expression() {
        exprTest("String(\"text\")", CarbonString("text"))
        envTest("""
            S = "test"
            R = String(S)
        """, "R", CarbonString("test"))
    }

    @Test
    fun infixOperators() {
        exprTest("\"hey\" + \" there\"", CarbonString("hey there"))
        exprTest("\"what is \" + \"up \" + \"my dudes?\"", CarbonString("what is up my dudes?"))
    }
}