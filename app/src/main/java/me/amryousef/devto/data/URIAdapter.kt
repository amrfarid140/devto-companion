package me.amryousef.devto.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.net.URI

object URIAdapter {
    @ToJson
    fun toJson(uri: URI) = uri.toString()

    @FromJson
    fun fromJson(uri: String) = URI.create(uri)
}