package me.amryousef.devto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URI

@JsonClass(generateAdapter = true)
data class Organisation(
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "profile_image")
    val profileImage: URI,
    @Json(name = "profile_image_90")
    val smallProfileImage: URI
)