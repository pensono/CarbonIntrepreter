package org.carbon

import org.antlr.v4.runtime.misc.Interval

class CarbonTypeException(message: String, location : Interval? = null) : CarbonException(message, location)