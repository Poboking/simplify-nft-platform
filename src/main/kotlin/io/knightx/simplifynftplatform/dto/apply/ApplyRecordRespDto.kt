package io.knightx.simplifynftplatform.dto.apply

import java.time.LocalDateTime

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午8:25
 */
data class ApplyRecordRespDto(
    val id: Long,
    val collectionName: String,
    val holdCollectionId: Long,
    val fromMemberName: String,
    val applyMemberName: String,
    val status: String,
    val createAt: LocalDateTime
) 