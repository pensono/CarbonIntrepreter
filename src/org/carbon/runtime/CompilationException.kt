package org.carbon.runtime

import org.antlr.v4.runtime.misc.Interval

/**
 * @author Ethan
 */
class CompilationException(message: String, location: Interval) : Exception(message)