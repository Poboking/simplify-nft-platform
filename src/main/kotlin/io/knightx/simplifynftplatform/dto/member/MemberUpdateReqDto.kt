package io.knightx.simplifynftplatform.dto.member

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午4:33
 */
data class MemberUpdateReqDto(
    val id: Long,
    val name: String,
    val password: String,
    val nickName: String
)
