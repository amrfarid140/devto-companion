package api

import api.model.Article

actual class ApiService {

    private val apiService = KtorArticlesApi("BNJUyn8jhYZoiihKgnwiT7fW")

    actual fun getArticles() = emptyList<Article>()
}