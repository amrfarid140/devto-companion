package api

import api.model.Article
import api.model.Tag
import kotlinx.coroutines.flow.Flow

interface ArticlesApi {
    suspend fun getArticles(page: Int = 1): List<Article>
    suspend fun getTags(): List<Tag>

    companion object {
        fun getInstance(): ArticlesApi = KtorArticlesApi()
    }
}