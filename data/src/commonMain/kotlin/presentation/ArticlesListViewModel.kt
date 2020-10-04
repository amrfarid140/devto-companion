package presentation

import api.KtorArticlesApi
import io.ktor.utils.io.*
import io.ktor.utils.io.core.internal.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

private suspend fun loadData(api: KtorArticlesApi, sendChannel: SendChannel<ArticlesListState>) {
    sendChannel.send(ArticlesListState.Loading)
    try {
        val r = api.getArticles()
        sendChannel.send(ArticlesListState.Ready(r, ""))
    } catch (e: Exception) {
        sendChannel.send(ArticlesListState.Error)
    }
}

@OptIn(DangerousInternalIoApi::class)
class ArticlesListViewModel {


    private val api = KtorArticlesApi()

    private val stateChannel = channelFlow {
        send(ArticlesListState.Loading)
        loadData(this)
    }.flowOn(Dispatchers.Main)

    val state: Flow<ArticlesListState>
        get() = stateChannel.flowOn(Dispatchers.Main)

    init {
        preventFreeze()
    }

    private suspend fun loadData(sendChannel: SendChannel<ArticlesListState>) {
        loadData(api, sendChannel)
    }
}