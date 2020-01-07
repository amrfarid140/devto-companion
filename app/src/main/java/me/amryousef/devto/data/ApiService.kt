package me.amryousef.devto.data

import io.reactivex.Single
import me.amryousef.devto.data.model.Article
import retrofit2.http.GET

interface ApiService {
    @GET("articles")
    fun getArticles(): Single<List<Article>>
}