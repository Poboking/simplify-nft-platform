package io.knightx.simplifynftplatform.dto.manager

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午3:29
 */
data class ManagerUpdateReqDto(
    val id: Long,
    val managerName: String,
    val password: String
)
