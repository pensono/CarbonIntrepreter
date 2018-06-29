package org.carbon.runtime

import org.carbon.PrettyPrintable

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
abstract class CarbonExpression : CarbonScope(), PrettyPrintable {
    //abstract val type : CarbonType // We don't really need types yet, so ignore this for now

    open fun eval(scope: CarbonScope): CarbonExpression = this

    override fun toString() = getFullString()
}