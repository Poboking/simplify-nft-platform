package io.knightx.simplifynftplatform.dto.hold

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.knightx.simplifynftplatform.po.HoldCollection
import java.time.LocalDateTime

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/26 下午2:44
 */
private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class MyHoldCollectionDetailRespDto(
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var id: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var collectionName: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var memberId: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var memberName: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var collectionId: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var applyRecordId: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var status: Int,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var statusName: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var tokenId: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var createAt: LocalDateTime,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    var hideFlag: Int,
) {
    fun toJson(): String = mapper.writeValueAsString(this)
    fun toEntity(): HoldCollection = mapper.convertValue(this, HoldCollection::class.java)

    companion object {
        fun fromJson(json: String) = mapper.readValue<HoldCollectionTransferDto>(json)
        fun fromEntity(
            entity: HoldCollection,
            collectionName: String,
            memberName: String,
            statusName: String,
            tokenId: String
        ) = MyHoldCollectionDetailRespDto(
            id = entity.id,
            memberId = entity.memberId,
            collectionId = entity.collectionId,
            applyRecordId = entity.applyRecordId,
            status = entity.status,
            createAt = entity.createAt,
            hideFlag = entity.hideFlag,
            collectionName = collectionName,
            tokenId = tokenId, memberName = memberName, statusName = statusName,
        )
    }
}