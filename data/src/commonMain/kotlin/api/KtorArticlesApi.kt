package api

import api.model.Article
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json

@Suppress("EXPERIMENTAL_API_USAGE")
class KtorArticlesApi(private val apiKey: String) : ArticlesApi {
    private companion object {
        const val baseUrl = "https://dev.to/api"
    }

    private fun httpClient() = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                    json = Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
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