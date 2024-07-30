package io.knightx.simplifynftplatform.dto.record

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午4:31
 */
data class ApplyRecordPageReqDto(
    val startTime: String,
    val endTime: String,
    val applyMemberId: Long,
    val holdMemberId: Long,
    val collectionId: Long,
    val status: Int
) 