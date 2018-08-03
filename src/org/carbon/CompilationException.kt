package org.carbon

import org.antlr.v4.runtime.misc.Interval

/**
 * @author Ethan
 */
// Interval is nullable for the convenience of writing error messages
class CompilationException(message: String, location: Interval? = null) : Exception(message)