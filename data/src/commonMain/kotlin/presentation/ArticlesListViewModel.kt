package presentation

import Executor
import MainDispatcher
import api.KtorArticlesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlin.coroutines.CoroutineContext

class ArticlesListViewModel constructor() : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + MainDispatcher
    private val api = KtorArticlesApi("")
    private val stateChannel =
            ConflatedBroadcastChannel<ArticlesListState>(
                    ArticlesListState.Started
            )
    val state: Flow<ArticlesListState> get() = stateChannel.asFlow()

    init {
        loadData()
    }

    private fun loadData() {
        stateChannel.offer(ArticlesListState.Loading)
        launch {
            try {
                val articles = api.getArticles()
                stateChannel.offer(ArticlesListState.Ready(articles, ""))
            } catch (exception: Exception) {
                stateChannel.offer(ArticlesListState.Error)
            }

        }
    }

    fun stop() {
        job.cancelChildren()
    }
}