package me.amryousef.devto.presentation

import api.ArticlesApi
import api.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ArticlesListViewModel(private val scope: CoroutineScope) {

    private val api: ArticlesApi = ArticlesApi.getInstance()

    private val stateChannel = MutableStateFlow<ArticlesListState>(ArticlesListState.Loading)

    fun onChanged(consumer: (ArticlesListState) -> Unit) {
        scope.launch {
            stateChannel.onStart { loadData() }.collect {
                consumer(it)
            }
        }
    }

    fun onLoadMore() {
        val currentPage = (stateChannel.value as? ArticlesListState.Ready)?.currentPage ?: 0
        scope.launch {
            val articles = api.getArticles(page = currentPage + 1)
            (stateChannel.value as? ArticlesListState.Ready)?.let { currentState ->
                stateChannel.value = currentState.copy(
                    data = currentState.data.toMutableList().apply {
                        addAll(articles.mapNotNull {
                            it.takeIf { it.coverImageUrl != null }?.toState()
                        })
                    }
                )
            }
        }
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
