package io.knightx.simplifynftplatform.dto.ipfs

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/18 下午3:10
 */
@Suppress("UNCHECKED_CAST")
private fun <T> ObjectMapper.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonNode) -> T, toJson: (T) -> String, isUnion: Boolean = false) = registerModule(SimpleModule().apply {
    addSerializer(k.java as Class<T>, object : StdSerializer<T>(k.java as Class<T>) {
        override fun serialize(value: T, gen: JsonGenerator, provider: SerializerProvider) = gen.writeRawValue(toJson(value))
    })
    addDeserializer(k.java as Class<T>, object : StdDeserializer<T>(k.java as Class<T>) {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext) = fromJson(p.readValueAsTree())
    })
})

val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    this.registerModules(JavaTimeModule())
}

data class TokenMetadata (

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val name: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val create: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val timestamp: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val data: String
) {
    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<TokenMetadata>(json)

        fun builder() = Builder()

        class Builder {
            private var name: String?= null
            private var create: String?= null
            private var timestamp: String?= null
            private var data: String?= null
            
            fun name(name: String) = apply { this.name = name }
            fun create(create: String) = apply { this.create = create }
            fun timestamp(timestamp: String) = apply { this.timestamp = timestamp }
            fun data(data: String) = apply { this.data = data }

            fun build() = TokenMetadata(
                name = requireNotNull(name) { "name is missing" },
                create = requireNotNull(create) { "create is missing" },
                timestamp = requireNotNull(timestamp) { "timestamp is missing" },
                data = requireNotNull(data) { "data is missing" }
            )
        }
    }
}

typealias Data = JsonNode
