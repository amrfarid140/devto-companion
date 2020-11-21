package api

import api.model.Article
import api.model.Tag
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

expect val KtorDispatcher: CoroutineDispatcher

class KtorArticlesApi : ArticlesApi {

    companion object {
        private const val BASE_URL = "https://dev.to/api/"
        private const val API_KEY = "BNJUyn8jhYZoiihKgnwiT7fW"
    }

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        defaultRequest {
            header("api-key", API_KEY)
        }
    }

    override suspend fun getArticles(page: Int): List<Article> {
        return withContext(KtorDispatcher) {
            httpClient.get {
                url("${BASE_URL}articles")
                parameter("page", page)
            }
        }
    }

    override suspend fun getTags(page: Int): List<Tag> {
        return withContext(KtorDispatcher) {
            httpClient.get {
                url("${BASE_URL}tags")
                parameter("page", page)
            }
        }
    }
}
