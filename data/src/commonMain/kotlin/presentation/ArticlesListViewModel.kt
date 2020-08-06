package presentation

import Executor
import MainDispatcher
import api.KtorArticlesApi
import api.model.Article
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlin.coroutines.CoroutineContext

class ArticlesListViewModel constructor(
        private val executor: Executor
) : CoroutineScope {
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

    fun loadData() {
        stateChannel.offer(ArticlesListState.Loading)
        launch {
            try {
                executor.executeInBackground(
                        block = { runBlocking { api.getArticles() } },
                        completion = { stateChannel.offer(ArticlesListState.Ready(it)) }
                )
            } catch (exception: Exception) {
                stateChannel.offer(ArticlesListState.Error)
            }

        }
    }

    fun stop() {
        job.cancelChildren()
    }
}