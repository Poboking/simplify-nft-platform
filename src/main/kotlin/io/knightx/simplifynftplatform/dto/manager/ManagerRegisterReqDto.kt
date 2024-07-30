package io.knightx.simplifynftplatform.dto.manager

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.knightx.simplifynftplatform.po.Manager

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/12 下午3:20
 */
private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class ManagerRegisterReqDto(
    val managerName: String,
    val password: String
) {
    fun toJson(): String = mapper.writeValueAsString(this)
    fun toEntity(): Manager = mapper.convertValue(this, Manager::class.java)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ManagerRegisterReqDto>(json)
    }
}