package me.amryousef.devto.presentation

sealed class ArticleState {
    data class Article(val title: String, val coverImageUrl: String, val userName: String): ArticleState()
    object Loading: ArticleState()
}