package api

import IODispatcher
import api.model.Article
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.utils.io.core.use
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Suppress("EXPERIMENTAL_API_USAGE")
class KtorArticlesApi(private val apiKey: String) : ArticlesApi {
    private companion object {
        const val baseUrl = "https://dev.to/api"
    }

    private fun httpClient() = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                    json = Json(JsonConfiguration(ignoreUnknownKeys = true, isLenient = true))
            )
        }
        defaultRequest {
            header("api-key", apiKey)
        }
    }

    override suspend fun getArticles(): List<Article> {
        return httpClient().use {
            it.get("${baseUrl}/articles")
        }
    }
}