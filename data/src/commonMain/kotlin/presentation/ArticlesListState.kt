package presentation

import api.model.Article

sealed class ArticlesListState {
    data class Ready(
        val data: List<Article>,
        val searchQuery: String
    ): ArticlesListState()
    object Error: ArticlesListState()
    object Loading: ArticlesListState()
    object Started: ArticlesListState()
}