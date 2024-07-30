package io.knightx.simplifynftplatform.dto.record

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.knightx.simplifynftplatform.po.ApplyRecord

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午3:50
 */
private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class ApplyRecordAddReqDto(
    val applyMemberId: Long,
    val fromMemberId: Long,
    val collectionId: Long,
) {
    fun toJson(): String = mapper.writeValueAsString(this)
    fun toEntity(): ApplyRecord = mapper.convertValue(this, ApplyRecord::class.java)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ApplyRecordAddReqDto>(json)
    }
}