package org.carbon.runtime

/**
 * @author Ethan Shea
 * @date 6/13/2018
 */
abstract class CarbonExpression : CarbonScope() {
    //abstract val type : CarbonType // We don't really need types yet, so ignore this for now

    open fun eval(): CarbonExpression = this
}