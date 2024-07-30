package io.knightx.simplifynftplatform.dto.member

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午7:19
 */
class MemberInfoRespDto(
    var memberName: String,
    
    val blockAddress: String,
    
    val publicKey: String,

    val privateKey: String,

    val nickName: String,
) 