package io.knightx.simplifynftplatform.dto.ipfs

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/30 下午4:02
 */
data class SaveRespDto(
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val url: String
)
