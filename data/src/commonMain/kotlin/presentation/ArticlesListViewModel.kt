package presentation

import Executor
import MainDispatcher
import api.KtorArticlesApi
import api.model.Article
import executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import runBlocking
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
            executor.executeInBackground(
                block = ::stuff,
                completion = {
                    stateChannel.offer(ArticlesListState.Ready(it, ""))
                }
            )
        }
    }

    private suspend fun stuff() =
        api.getArticles()

    fun stop() {
        job.cancelChildren()
    }
}