package me.amryousef.devto.presentation

import com.babylon.orbit.LifecycleAction
import com.babylon.orbit.OrbitViewModel
import me.amryousef.devto.data.ApiService
import me.amryousef.devto.data.model.Article
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit

sealed class State {
    object Loading : State()
    object Error : State()
    data class Ready(val articles: List<ArticleState>) : State() {
        data class ArticleState(
            val imageUrl: URI?,
            val title: String,
            val authorImage: URI,
            val authorName: String,
            val tags: List<String>,
            val lengthInTime: String?,
            val publishedDate: String,
            val minutesSincePublishing: Long
        )
    }
}

object LoadArticles

class ArticlesViewModel(
    private val apiService: ApiService,
    private val articlesStateReducer: ArticlesStateReducer
) : OrbitViewModel<State, Nothing>(State.Loading, {
    perform("load articles")
        .on(LifecycleAction.Created::class.java, LoadArticles::class.java)
        .transform {
            apiService.getArticles().toObservable().map {
                Result.success(it)
            }.onErrorReturn { Result.failure(it) }
        }
        .reduce { articlesStateReducer.reduce(this.currentState, this.event) }
})

class ArticlesStateReducer {
    fun reduce(
        @Suppress("UNUSED_PARAMETER") currentState: State,
        apiArticlesResult: Result<List<Article>>
    ) = apiArticlesResult.getOrNull()?.let { apiArticles ->
        State.Ready(
            apiArticles.map { apiArticle ->
                State.Ready.ArticleState(
                    title = apiArticle.title,
                    authorName = apiArticle.user.name,
                    authorImage = apiArticle.user.smallProfileImage,
                    tags = apiArticle.tags,
                    imageUrl = apiArticle.coverImage,
                    lengthInTime = null,
                    minutesSincePublishing = getMinutesSincePublishing(apiArticle.publishedAt),
                    publishedDate = apiArticle.publishDate
                )
            }
        )
    } ?: State.Error

    private fun getMinutesSincePublishing(publishedAt: Date): Long {
        val difference = publishedAt.time - Date().time
        if (TimeUnit.MILLISECONDS.toMinutes(difference) < 60) {
            return TimeUnit.MILLISECONDS.toMinutes(difference)
        } else if (TimeUnit.MILLISECONDS.toHours(difference) < 1) {
            return TimeUnit.MILLISECONDS.toHours((difference))
        }
        return TimeUnit.MILLISECONDS.toDays((difference))
    }
}