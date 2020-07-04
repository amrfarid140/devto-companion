package api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("cover_image")
    val coverImageUrl: String?,
    @SerialName("readable_publish_date")
    val publishDate: String,
    @SerialName("social_image")
    val socialImage: String,
    @SerialName("tag_list")
    val tags: List<String>,
    @SerialName("canonical_url")
    val url: String,
    @SerialName("user")
    val user: User
)

@Serializable
data class User(
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String,
    @SerialName("twitter_username")
    val twitterUsername: String?,
    @SerialName("website_url")
    val website: String?,
    @SerialName("profile_image")
    val profileImageUrl: String
)