package org.carbon

import org.carbon.syntax.wrapString
import org.junit.Test

/**
 * @author Ethan Shea
 * *
 * @date 6/13/2018
 */
class StringTests {
    @Test
    fun literals() {
        exprTest("\"Test\"", wrapString("Test"))
        exprTest(" \"Test\"  ", wrapString("Test"))
        exprTest("\"Test with spaces\"", wrapString("Test with spaces"))
    }

    @Test
    fun newlines() {
        exprTest("\"\\n\"", wrapString("\n"))
        exprTest("\"one\\ntwo\"", wrapString("one\ntwo"))
    }

    @Test
    fun unicode() {
        exprTest("\"السلام عليكم\"", wrapString("السلام عليكم"))
        exprTest("\"Dobrý den\"", wrapString("Dobrý den"))
        exprTest("\"Hello\"", wrapString("Hello"))
        exprTest("\"שָׁלוֹם\"", wrapString("שָׁלוֹם"))
        exprTest("\"नमस्ते\"", wrapString("नमस्ते"))
        exprTest("\"こんにちは\"", wrapString("こんにちは"))
        exprTest("\"你好\"", wrapString("你好"))
        exprTest("\"Olá\"", wrapString("Olá"))
        exprTest("\"Здравствуйте\"", wrapString("Здравствуйте"))
        exprTest("\"Hola\"", wrapString("Hola"))
    }

//    @Test // Not sure if I want to support this
//    fun expression() {
//        exprTest("String(\"text\")", wrapString("text"))
//        envTest("""
//            S = "test"
//            R = String(S)
//        """, "R", wrapString("test"))
//    }

    @Test
    fun infixOperators() {
        exprTest("\"hey\" + \" there\"", wrapString("hey there"))
        exprTest("\"what is \" + \"up \" + \"my dudes?\"", wrapString("what is up my dudes?"))
    }
}