package me.amryousef.devto.presentation

sealed class ArticlesListState {
    data class Ready(
        val currentPage: Int,
        val data: List<ArticleState>,
        val tags: List<TagState>,
        val searchQuery: String
    ): ArticlesListState() {
        data class ArticleState(val title: String, val coverImageUrl: String, val userName: String)
        data class TagState(val id: Long, val name: String, val isSelected: Boolean)
    }
    data class Error(val error: Exception): ArticlesListState()
    object Loading: ArticlesListState()
}