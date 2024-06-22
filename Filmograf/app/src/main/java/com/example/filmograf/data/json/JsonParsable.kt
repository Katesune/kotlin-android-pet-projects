package com.example.filmograf.data.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

internal abstract class JsonProp<T> (private val prop: String) {
    abstract val default: T

    fun extract(jsonObject: JsonObject): T {
        return jsonObject.get(prop)?.let { elem ->
            if (elem.isJsonNull) this.default
            else this.getValue(elem)
        } ?: this.default
    }

    abstract fun getValue(elem: JsonElement): T
}

internal class StringJsonProp(prop: String) : JsonProp<String>(prop) {
    override val default = ""
    override fun getValue(elem: JsonElement): String = elem.asString
}

internal class IntJsonProp(prop: String) : JsonProp<Int>(prop) {
    override val default = 0
    override fun getValue(elem: JsonElement): Int = elem.asInt
}

internal class FloatJsonProp(prop: String) : JsonProp<Float>(prop) {
    override val default = 0f
    override fun getValue(elem: JsonElement): Float = elem.asFloat
}

internal class ObjectJsonProp(prop: String) : JsonProp<JsonObject>(prop) {
    override val default = JsonObject()
    override fun getValue(elem: JsonElement): JsonObject = elem.asJsonObject
}

internal class ArrayJsonProp(prop: String) : JsonProp<JsonArray>(prop) {
    override val default = JsonArray()
    override fun getValue(elem: JsonElement): JsonArray = elem.asJsonArray
}

internal interface JsonRedirected {
    fun JsonObject.extractObject(prop: String): JsonObject {
        return ObjectJsonProp(prop).extract(this)
    }

    fun JsonObject.extractArray(prop: String): JsonArray {
        return ArrayJsonProp(prop).extract(this)
    }
}