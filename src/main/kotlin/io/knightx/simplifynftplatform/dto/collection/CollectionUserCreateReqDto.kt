package io.knightx.simplifynftplatform.dto.collection

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.knightx.simplifynftplatform.po.Collection

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午8:42
 */

private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class CollectionUserCreateReqDto(
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val name: String,
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val content: String,
) {
    fun toJson(): String = mapper.writeValueAsString(this)
    fun toEntity(): Collection = mapper.convertValue(this, Collection::class.java)

    companion object {
        fun fromJson(json: String) = mapper.readValue<CollectionUserCreateReqDto>(json)
    }
}