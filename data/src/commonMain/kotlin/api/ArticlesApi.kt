package api

import api.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesApi {
    suspend fun getArticles(): List<Article>
}