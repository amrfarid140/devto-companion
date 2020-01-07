package me.amryousef.devto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URI

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "twitter_username")
    val twitter: String?,
    @Json(name = "github_username")
    val github: String?,
    @Json(name = "website_url")
    val website: URI?,
    @Json(name = "profile_image")
    val profileImage: URI,
    @Json(name = "profile_image_90")
    val smallProfileImage: URI
)