package io.knightx.simplifynftplatform.ext

import io.knightx.simplifynftplatform.exception.param.ParamIllegalityException
import io.knightx.simplifynftplatform.exception.param.TimeIllegalityPatternException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午3:46
 */

fun String.toErrorMsg() = "${LocalDateTime.now()}  [Error:  $this]"

fun getPatternTime(time: String): LocalDateTime {
    val parse: LocalDateTime
    try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        parse = LocalDateTime.parse(time, formatter)
    } catch (e: Exception) {
        throw TimeIllegalityPatternException("Collection time pattern error $e".toErrorMsg())
    }
    return parse
}

fun safeToLong(value: String): Long {
    return try {
        value.toLong()
    } catch (e: Exception) {
        throw ParamIllegalityException("Param illegality , as result of convert to long $e".toErrorMsg())
    }
}

