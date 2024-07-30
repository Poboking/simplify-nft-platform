package io.knightx.simplifynftplatform.ext

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import io.knightx.simplifynftplatform.dto.general.PageDto
import java.time.LocalDateTime

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午4:00
 */
fun <K, V> PageDto<K, V>.pageQuery(func: () -> List<K>): PageInfo<K> {
    try {
        PageHelper.startPage<K>(this.pageNum, this.pageSize)
        return PageInfo(func(), this.pageSize)
    } catch (e: Exception) {
        throw e
    } finally {
        PageHelper.clearPage()
    }
}

fun <T> T.pageQuery(pageNum: Int, pageSize: Int, func: () -> List<*>): PageInfo<*> {
    try {
        PageHelper.startPage<T>(pageNum, pageSize)
        return PageInfo(func(), pageSize)
    } catch (e: Exception) {
        throw e
    } finally {
        PageHelper.clearPage()
    }
}

fun <T> convertToResultPageInfo(from: PageInfo<*>, func: () -> List<T>): PageInfo<T> {
    return PageInfo<T>().apply {
        this.list = func()
        this.pageNum = from.pageNum
        this.pageSize = from.pageSize
        this.size = from.size
        this.startRow = from.startRow
        this.endRow = from.endRow
        this.pages = from.pages
        this.prePage = from.prePage
        this.nextPage = from.nextPage
//        this.isFirstPage = isFirstPage
//        this.isLastPage = isLastPage
//        this.hasPreviousPage = hasPreviousPage
//        this.hasNextPage = hasNextPage
        this.navigatePages = from.navigatePages
        this.navigatepageNums = from.navigatepageNums
        this.navigateFirstPage = from.navigateFirstPage
        this.navigateLastPage = from.navigateLastPage
    }
}


fun checkQueryTime(startTime: String, endTime: String): Pair<LocalDateTime, LocalDateTime> {
    var end: LocalDateTime = LocalDateTime.now()
    var start: LocalDateTime = LocalDateTime.now().minusDays(30)
    if (endTime.isNotBlank()) {
        end = getPatternTime(endTime)
    }
    if (startTime.isNotBlank()) {
        start = getPatternTime(startTime)
    }
    return Pair(start, end)
}