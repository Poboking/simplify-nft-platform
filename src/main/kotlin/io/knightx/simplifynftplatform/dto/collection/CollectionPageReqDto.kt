package io.knightx.simplifynftplatform.dto.collection

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午7:25
 */
data class CollectionPageReqDto(
    val memberName: String,
    val name: String,
    val startTime: String,
    val endTime: String
)
