package io.knightx.simplifynftplatform.dto.auth

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午8:07
 */
private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class LoginReqDto(
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val name: String,
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val password: String
)