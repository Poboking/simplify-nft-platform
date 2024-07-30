package io.knightx.simplifynftplatform.dto.general

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午4:16
 */
data class PageDto<K,V>(
    override var pageNum: Int = 1,
    override var pageSize: Int = 10,
    var query: V
) : PageEntity

interface PageEntity {
    val pageNum: Int
    val pageSize: Int
}