package api

import api.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesApi {
    suspend fun getArticles(page: Int = 1): List<Article>

    companion object {
        fun getInstance(): ArticlesApi = KtorArticlesApi()
    }
}