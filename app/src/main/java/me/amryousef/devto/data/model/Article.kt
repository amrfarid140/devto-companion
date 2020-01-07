package me.amryousef.devto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URI
import java.util.*

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "cover_image")
    val coverImage: URI,
    @Json(name = "readable_publish_date")
    val publishDate: String,
    @Json(name = "tag_list")
    val tags: List<String>,
    @Json(name = "url")
    val url: URI,
    @Json(name = "published_at")
    val publishedAt: Date,
    @Json(name = "user")
    val user: User,
    @Json(name = "organization")
    val organisation: Organisation
)