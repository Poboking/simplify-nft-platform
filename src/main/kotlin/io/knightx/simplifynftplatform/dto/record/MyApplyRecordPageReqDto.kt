package io.knightx.simplifynftplatform.dto.record

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午4:37
 */
data class MyApplyRecordPageReqDto(
    val startTime: String,
    val endTime: String,
    val holdMemberId: Long,
    val collectionId: Long,
    val status: Int
) 