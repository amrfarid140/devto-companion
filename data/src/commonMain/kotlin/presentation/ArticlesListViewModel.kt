package presentation

import BackgroundDispatcher
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
        get() = job + newSingleThreadContext("Amr")
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
            val r = KtorArticlesApi("").getArticles()
            stateChannel.offer(ArticlesListState.Ready(r, ""))
            print(this.coroutineContext)
//            executor.executeInBackground(
//                block = ::stuff,
//                completion = {
//                    stateChannel.offer(ArticlesListState.Ready(it, ""))
//                }
//            )
        }
    }

    fun stop() {
        job.cancelChildren()
    }
}