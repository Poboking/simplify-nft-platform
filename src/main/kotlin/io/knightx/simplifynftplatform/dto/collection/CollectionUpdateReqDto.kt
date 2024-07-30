package io.knightx.simplifynftplatform.dto.collection

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.knightx.simplifynftplatform.po.Collection

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午9:30
 */
private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class CollectionUpdateReqDto(
    val id: Long,
    val name: String,
    val content: String,
) {
    fun toJson(): String = mapper.writeValueAsString(this)
    fun toEntity(): Collection = mapper.convertValue(this, Collection::class.java)

    companion object {
        fun fromJson(json: String) = mapper.readValue<CollectionUpdateReqDto>(json)
    }
}
