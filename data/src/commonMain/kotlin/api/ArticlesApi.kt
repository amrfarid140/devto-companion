package api

import api.model.Article
import api.model.Tag
import kotlinx.coroutines.flow.Flow

interface ArticlesApi {
    suspend fun getArticles(page: Int = 1, tags: List<String> = emptyList()): List<Article>
    suspend fun getTags(page: Int = 1): List<Tag>

    companion object {
        fun getInstance(): ArticlesApi = KtorArticlesApi()
    }
}