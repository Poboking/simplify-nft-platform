package io.knightx.simplifynftplatform.dto.hold

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午10:38
 */
data class HoldCollectionPageReqDto(
    val memberId: Long,
    val collectionId: Long,
    val startTime: String,
    val endTime: String
) 