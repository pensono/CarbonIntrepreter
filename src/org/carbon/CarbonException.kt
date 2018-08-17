package org.carbon

import org.antlr.v4.runtime.misc.Interval

/**
 * @author Ethan
 */
open class CarbonException(msg: String, val location : Interval? = null) : Exception(msg)