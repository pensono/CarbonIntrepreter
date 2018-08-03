package org.carbon

import org.carbon.syntax.wrapInteger
import org.junit.Test

/**
 * @author Ethan
 */
class ExpressionTests {
    @Test
    fun basicFunction() {
        envTest("""
            Double(Integer) = Integer * 2
            R=Double(4)
            """, "R", wrapInteger(8))
        envTest("""
            Double(Integer) = Integer * 2
            R=Double(4)+1
            """, "R", wrapInteger(9))
        envTest("""
            Double(Integer) = Integer * 2
            R=1+Double(4)
        """, "R", wrapInteger(9))
    }

    @Test
    fun multipleArgFunction() {
        envTest("""
            Sum(A:Integer, B:Integer) = A + B
            R=Sum(1,2)
            """, "R", wrapInteger(3))
        envTest("""
            Sum(A:Integer, B:Integer, C:Integer) = A + B + C
             R=Sum(1,2,3)
             """, "R", wrapInteger(6))
    }

    @Test
    fun functionIgnoresArgument() {
        envTest("""
            F(A:Integer) = 4
            R=F(3)
            """, "R", wrapInteger(4))
    }

    @Test
    fun functionCallsFunction() {
        envTest("""
            Double(Integer) = Integer * 2
            Quadruple(Integer) = 2 * Double(Integer)
            R=Quadruple(4)
        """, "R", wrapInteger(16))
    }

    @Test
    fun noArgFunction() {
        envTest("""
            Four() = 4
            R=Four()
        """, "R", wrapInteger(4))
    }

    @Test
    fun returnFunction(){
        envTest("""
            F(C:Integer) = G
            G(C:Integer) = C * 2
            R = F(2)(3)
        """, "R", wrapInteger(2*3))
    }

    @Test
    fun lexicallyScoped() {
        envTest("""
            C = 5
            F(C:Integer) = C + G(C)
            G(D:Integer) = D + C
            R = F(2)
        """, "R", wrapInteger(2+2+5)) // Will be 2 + 2 + 2 under dynamic scoping
    }

    @Test
    fun partialFunctionApplication() {
        envTest("""
            Func(A:Integer, B:Integer) = A * B + B
            F = Func(2,)
            R = F(4)
        """, "R", wrapInteger(2*4+4))

        envTest("""
            Func(A:Integer, B:Integer) = A * B + B
            F = Func(,2)
            R = F(4)
        """, "R", wrapInteger(4*2+2))
    }

    @Test
    fun recursive() {
        envTest("""
            SlowMult(A: Integer, B: Integer)
                | B > 1 = A + SlowMult(A, B-1)
                = A
            R = SlowMult(5,7)
        """, "R", wrapInteger(35))
    }

    @Test
    fun complexRecursive() {
        envTest(""" // The classic example
            Fib(N: Integer) | N == 0 = 0
                            | N == 1 = 1
                            = Fib(N-1) + Fib(N-2)
            R = Fib(14)
        """, "R", wrapInteger(377))
    }
}