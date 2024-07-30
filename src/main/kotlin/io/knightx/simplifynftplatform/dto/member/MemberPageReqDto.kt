package io.knightx.simplifynftplatform.dto.member

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午4:18
 */
data class MemberPageReqDto(
    val startTime: String,
    val endTime: String,
    val name: String?,
)