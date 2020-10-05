package me.amryousef.devto.presentation

import api.ArticlesApi
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

    private suspend fun loadData() {
        ArticlesListState.Loading
        try {
            val r = api.getArticles()
            stateChannel.value = ArticlesListState.Ready(
                r.mapNotNull {
                    it.takeIf { it.coverImageUrl != null }?.let { article ->
                        ArticlesListState.Ready.ArticleState(
                            article.title,
                            article.coverImageUrl!!,
                            article.user.name
                        )
                    }
                },
                ""
            )
        } catch (e: Exception) {
            stateChannel.value = ArticlesListState.Error
        }
    }
}
