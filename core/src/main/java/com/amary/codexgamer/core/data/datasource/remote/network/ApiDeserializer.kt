package com.amary.codexgamer.core.data.datasource.remote.network

import com.google.gson.*
import java.lang.reflect.Type

class ApiDeserializer<T> : JsonDeserializer<T> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T = Gson().run {
        val resultJsonObject = json?.asJsonObject?.get("results")?.asJsonArray
        fromJson(resultJsonObject, typeOfT)
    }
}