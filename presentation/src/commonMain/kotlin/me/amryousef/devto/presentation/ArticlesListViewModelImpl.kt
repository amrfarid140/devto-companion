package me.amryousef.devto.presentation

import api.ArticlesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class ArticlesListViewModelImpl(
    override val coroutineContext: CoroutineContext,
) : ArticlesListViewModel, CoroutineScope {
    private val api: ArticlesApi = ArticlesApi.getInstance()

    private val mutableState =
        MutableStateFlow<ArticlesListState>(ArticlesListState.Loading)

    override val state: Flow<ArticlesListState> = mutableState

    init {
        launch {
            mutableState.value = try {
                ArticlesListState.Ready(
                    tagsState = PageState(1, loadTagsAsync().await()),
                    articlesState = PageState(1, loadArticlesAsync().await())
                )
            } catch (exception: Exception) {
                ArticlesListState.Error(exception)
            }
        }
    }

    override fun loadMoreArticles() {
        (mutableState.value as? ArticlesListState.Ready)?.let { readyState ->
            val existingArticles = readyState.articlesState.items
            mutableState.value = readyState.copy(
                articlesState = readyState.articlesState.copy(
                    items = existingArticles.toMutableList().apply {
                        add(ArticleState.Loading)
                    }
                )
            )
            launch {
                try {
                    val newArticles = loadArticlesAsync().await()
                    mutableState.value = readyState.copy(
                        articlesState = readyState.articlesState.copy(
                            page = readyState.articlesState.page.inc(),
                            items = existingArticles.toMutableList().apply {
                                addAll(newArticles)
                            }
                        )
                    )
                } catch (exception: Exception) {

                }
            }
        }
    }

    private fun loadArticlesAsync() = async {
        val pageToLoad = (mutableState.value as? ArticlesListState.Ready)
            ?.articlesState?.page?.inc() ?: 1
        val selectedTags: List<String> =
            (mutableState.value as? ArticlesListState.Ready)?.tagsState?.items
                ?.asSequence()
                ?.filter { (it as? TagState.Tag)?.isSelected == true }
                ?.map { (it as TagState.Tag).name }?.toList() ?: emptyList()
        api.getArticles(page = pageToLoad, tags = selectedTags).mapNotNull {
            it.takeIf { !it.coverImageUrl.isNullOrBlank() }?.let { article ->
                ArticleState.Article(
                    title = article.title,
                    coverImageUrl = article.coverImageUrl!!,
                    userName = article.user.name
                )
            }
        }
    }

    private fun loadTagsAsync() = async {
        val pageToLoad = (mutableState.value as? ArticlesListState.Ready)
            ?.tagsState?.page?.inc() ?: 1
        api.getTags(page = pageToLoad).map { tag ->
            TagState.Tag(
                id = tag.id,
                name = tag.name,
                isSelected = false
            )
        }
    }

    override fun loadMoreTags() {
        (mutableState.value as? ArticlesListState.Ready)?.let { readyState ->
            val existingTags = readyState.tagsState.items
            mutableState.value = readyState.copy(
                tagsState = readyState.tagsState.copy(
                    items = existingTags.toMutableList().apply {
                        add(TagState.Loading)
                    }
                )
            )
            launch {
                try {
                    val newTags = loadTagsAsync().await()
                    mutableState.value = readyState.copy(
                        tagsState = readyState.tagsState.copy(
                            page = readyState.tagsState.page.inc(),
                            items = existingTags.toMutableList().apply {
                                addAll(newTags)
                            }
                        )
                    )
                } catch (exception: Exception) {

                }
            }
        }
    }

    override fun onTagSelected(tag: TagState.Tag) {
        (mutableState.value as? ArticlesListState.Ready)?.let { readyState ->
            val updatedStated = readyState.copy(
                articlesState = readyState.articlesState.copy(page = 0),
                tagsState = readyState.tagsState.copy(
                    items = readyState.tagsState.items.map { currentTag ->
                        if (currentTag == tag) {
                            tag.copy(
                                isSelected = !tag.isSelected
                            )
                        } else {
                            currentTag
                        }
                    }
                )
            )
            launch {
                mutableState.value = updatedStated
                try {
                    val updatedArticles = loadArticlesAsync().await()
                    mutableState.value = updatedStated.copy(
                        articlesState = PageState(1, updatedArticles)
                    )
                } catch (exception: Exception) {

                }
            }
        }
    }

    override fun cancel() {
        coroutineContext.cancelChildren()
    }
}