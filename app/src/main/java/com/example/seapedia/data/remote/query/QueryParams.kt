package com.example.seapedia.data.remote.query
import kotlinx.serialization.json.*
interface QueryParams
inline fun <reified T : QueryParams> T.toMap(): Map<String, String> {
    val jsonElement = Json.encodeToJsonElement(this)

    return jsonElement.jsonObject
        .filterValues { it != JsonNull }
        .mapValues { (_, value) ->
            value.jsonPrimitive.content
        }
}