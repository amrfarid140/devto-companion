package api

import api.model.Article
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

expect val KtorDispatcher: CoroutineDispatcher

class KtorArticlesApi : ArticlesApi {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override suspend fun getArticles(page: Int): List<Article> {
        return withContext(KtorDispatcher) {
            httpClient.get {
                url("https://dev.to/api/articles")
                parameter("page", page)
                headers {
                    append("api-key", "BNJUyn8jhYZoiihKgnwiT7fW")
                }
            }
        }
    }
}
