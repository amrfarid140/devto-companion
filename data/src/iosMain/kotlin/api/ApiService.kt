package api

import kotlinx.coroutines.runBlocking

actual class ApiService {

    actual fun getArticles() =
        runBlocking {
            KtorArticlesApi("BNJUyn8jhYZoiihKgnwiT7fW").getArticles()
        }
}