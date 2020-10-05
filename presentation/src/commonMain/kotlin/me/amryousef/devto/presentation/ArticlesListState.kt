package me.amryousef.devto.presentation

sealed class ArticlesListState {
    data class Ready(
        val data: List<ArticleState>,
        val searchQuery: String
    ): ArticlesListState() {
        data class ArticleState(val title: String, val coverImageUrl: String, val userName: String)
    }
    object Error: ArticlesListState()
    object Loading: ArticlesListState()
}