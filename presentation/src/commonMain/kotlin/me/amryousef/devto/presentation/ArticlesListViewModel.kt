package me.amryousef.devto.presentation

import api.ArticlesApi
import api.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface ArticlesListViewModel {
    val state: Flow<ArticlesListState>
    fun onLoadMore()
    fun cancel()
}

class ArticlesListViewModelImpl(override val coroutineContext: CoroutineContext): ArticlesListViewModel, CoroutineScope {

    private val api: ArticlesApi = ArticlesApi.getInstance()

    private val stateChannel =
        MutableStateFlow<ArticlesListState>(ArticlesListState.Loading)

    override val state: Flow<ArticlesListState> = stateChannel.onStart { loadData() }

    override fun onLoadMore() {
        val currentPage = (stateChannel.value as? ArticlesListState.Ready)?.currentPage ?: 0
        launch {
            val articles = api.getArticles(page = currentPage + 1)
            (stateChannel.value as? ArticlesListState.Ready)?.let { currentState ->
                val updatedItems = currentState.data.toMutableList().apply {
                    addAll(articles.mapNotNull { newArticle ->
                        newArticle.takeIf {
                            it.coverImageUrl != null && currentState.data.find { articleState -> articleState.title == newArticle.title } == null
                        }?.toState()
                    })
                }
                stateChannel.value = currentState.copy(
                    currentPage = currentPage + 1,
                    data = updatedItems
                )
            }
        }
    }

    override fun cancel() {
        coroutineContext.cancelChildren()
    }

    private suspend fun loadData() {
        ArticlesListState.Loading
        try {
            val r = api.getArticles()
            stateChannel.value = ArticlesListState.Ready(
                1,
                r.mapNotNull {
                    it.takeIf { it.coverImageUrl != null }?.toState()
                },
                ""
            )
        } catch (e: Exception) {
            stateChannel.value = ArticlesListState.Error
        }
    }

    private fun Article.toState() = ArticlesListState.Ready.ArticleState(
        title,
        coverImageUrl!!,
        user.name
    )
}
