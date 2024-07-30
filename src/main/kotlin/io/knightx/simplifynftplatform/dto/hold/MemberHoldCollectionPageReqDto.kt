package io.knightx.simplifynftplatform.dto.hold

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/26 下午5:29
 */
data class MemberHoldCollectionPageReqDto(
    val collectionName: String,
    val startTime: String,
    val endTime: String
)