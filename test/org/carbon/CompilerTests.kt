package org.carbon

import org.carbon.syntax.wrapInteger
import org.junit.Test

/**
 * @author Ethan
 */
class CompilerTests {
    @Test
    fun basicDefinition() {
        envTest("A=3", "A", wrapInteger(3))
    }

    @Test
    fun basicDerivedVariable() {
        envTest("A=3 B=A+2", "B", wrapInteger(5))
    }

    @Test
    fun identifierWithNumber() {
        envTest("Var1 = 2", "Var1", wrapInteger(2))
    }

    @Test
    fun outOfOrderDependencies() {
        envTest("""
            C = A + B
            A = 1
            B = 2
        """, "C", wrapInteger(3))
    }

    @Test
    fun dotNotation() {
        // A little strange to do it with an infix operator, but it should work anyways
        exprTest("1.+(1)", wrapInteger(2))
        exprTest("34.+(57)", wrapInteger(91))
        exprTest("-34.+(57)", wrapInteger(23))
    }

    @Test
    fun dotsOnBothSides() {
        envTest("""
            T = { Member = {Integer = 1}() }
            A = T()
            B = T()
            R = A.Member.Integer + B.Member.Integer
        """, "R", wrapInteger(2))
    }

    @Test
    fun nestedScopes() {
        envTest("""
            E = 5
            A = {
                C = E,
                B = {
                    D = C,
                }()
            }
            R = A().B.D
        """, "R", wrapInteger(5))
    }

    @Test
    fun guards() {
        envTest("""
            Choice(A:Integer) | A < 0 = 4
                              = 5
            Option1 = Choice(-2)
        """, "Option1", wrapInteger(4))

        envTest("""
            Choice(A:Integer) | A < 0 = 4
                              = 5
            Option2 = Choice(3)
        """, "Option2", wrapInteger(5))
    }

    @Test(expected = CompilationException::class)
    fun badImplicitlyNamedArgFails() {
        testEval("""
            F(NotAType) = NotAType
            F(2)
            """)
    }
}