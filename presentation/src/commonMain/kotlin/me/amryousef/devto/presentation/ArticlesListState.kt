package me.amryousef.devto.presentation

sealed class ArticlesListState {
    data class Ready(
        val articlesState: PageState<ArticleState>,
        val tagsState: PageState<TagState>
    ): ArticlesListState()
    data class Error(val error: Exception): ArticlesListState()
    object Loading: ArticlesListState()
}