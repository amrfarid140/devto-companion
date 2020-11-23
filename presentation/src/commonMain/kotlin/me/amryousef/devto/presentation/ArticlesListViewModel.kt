package me.amryousef.devto.presentation

import kotlinx.coroutines.flow.Flow

interface ArticlesListViewModel {
    val state: Flow<ArticlesListState>
    fun loadMoreArticles()
    fun loadMoreTags()
    fun onTagSelected(tag: TagState.Tag)
    fun cancel()
}
