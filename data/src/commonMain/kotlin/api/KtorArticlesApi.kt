package api

import api.model.Article
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.internal.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

expect val KtorDispatcher: CoroutineDispatcher

@OptIn(DangerousInternalIoApi::class)
class KtorArticlesApi : ArticlesApi {

    @OptIn(DangerousInternalIoApi::class)
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

    init {
        preventFreeze()
    }

    override suspend fun getArticles(): List<Article> {
        return api.getArticles(httpClient)
    }
}

private suspend fun getArticles(httpClient: HttpClient): List<Article> =
    withContext(KtorDispatcher) {
        httpClient.get {
            url("https://dev.to/api/articles")
            headers {
                append("api-key", "BNJUyn8jhYZoiihKgnwiT7fW")
            }
        }
    }