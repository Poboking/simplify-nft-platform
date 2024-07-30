package io.knightx.simplifynftplatform.ext

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午4:39
 */
fun <T> T.check(): Boolean {
    return when (this) {
        is String -> this.isNotBlank()
        is Collection<*> -> this.isNotEmpty()
        is Map<*, *> -> this.isNotEmpty()
        is Array<*> -> this.isNotEmpty()
        else -> this != null
    }
}

