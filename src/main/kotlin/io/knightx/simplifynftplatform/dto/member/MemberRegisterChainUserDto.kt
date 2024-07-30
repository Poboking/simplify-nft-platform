package io.knightx.simplifynftplatform.dto.member

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.knightx.simplifynftplatform.po.Member

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/22 下午5:50
 */
private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class MemberRegisterChainUserDto(
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val memberId: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val username: String
) {
    fun toJson(): String = mapper.writeValueAsString(this)
    fun toEntity(): Member = mapper.convertValue(this, Member::class.java)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MemberRegisterChainUserDto>(json)
    }
}
