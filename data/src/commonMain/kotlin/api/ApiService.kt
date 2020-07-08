package api

import api.model.Article

expect class ApiService {
    fun getArticles(): List<Article>
}