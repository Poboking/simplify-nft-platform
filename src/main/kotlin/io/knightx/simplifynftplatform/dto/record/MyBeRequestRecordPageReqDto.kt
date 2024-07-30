package io.knightx.simplifynftplatform.dto.record

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/27 上午12:22
 */
data class MyBeRequestRecordPageReqDto(
    val startTime: String,
    val endTime: String,
    val applyMemberId: Long,
    val collectionId: Long,
    val status: Int
)