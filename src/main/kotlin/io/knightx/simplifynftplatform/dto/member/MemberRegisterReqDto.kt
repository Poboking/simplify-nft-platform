package io.knightx.simplifynftplatform.dto.member

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午2:55
 */
// To parse the JSON, install jackson-module-kotlin and do:
//
//   val userDto = UserDto.fromJson(jsonString)

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.knightx.simplifynftplatform.po.Member

private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class MemberRegisterReqDto (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
//    @get:JsonProperty("member_name", required=true)@field:JsonProperty("member_name", required=true)
    val memberName: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val password: String
) {
    fun toJson(): String = mapper.writeValueAsString(this)
    fun toEntity(): Member = mapper.convertValue(this, Member::class.java)
    companion object {
        fun fromJson(json: String) = mapper.readValue<MemberRegisterReqDto>(json)
    }
}
