package com.example.seapedia.data.remote.query

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class AllProductQuery(
    @SerialName("store_id")
    val storeId: Int? = null,
    val name: String? = null,
    val limit: Int? = null
)

fun AllProductQuery.toMap() : Map<String, String>{
    val jsonElement = Json.encodeToJsonElement(this)
    return jsonElement.jsonObject.filterValues {
        value ->
        value != JsonNull
    }.mapValues {
        (_,value) ->
        value.jsonPrimitive.content
    }
}