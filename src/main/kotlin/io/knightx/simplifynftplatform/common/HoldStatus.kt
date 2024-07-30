package io.knightx.simplifynftplatform.common

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午2:35
 */
enum class HoldStatus(val value: Int) {
    HOLDING(1),
    RELEASED(2);

    companion object {
        fun getName(value: Int): String {
            return when (value) {
                1 -> "Holding"
                2 -> "Released"
                else -> "Unknown"
            }
        }
    }
}