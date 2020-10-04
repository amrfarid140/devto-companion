package presentation

import api.KtorArticlesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

class ArticlesListViewModel {

    private val api = KtorArticlesApi()

    private val stateChannel = channelFlow {
        send(ArticlesListState.Loading)
        loadData(this)
    }.flowOn(Dispatchers.Main)

    val state: Flow<ArticlesListState>
        get() = stateChannel.flowOn(Dispatchers.Main)

    private suspend fun loadData(sendChannel: SendChannel<ArticlesListState>) {
        sendChannel.send(ArticlesListState.Loading)
        try {
            val r = api.getArticles()
            sendChannel.send(ArticlesListState.Ready(r, ""))
        } catch (e: Exception) {
            sendChannel.send(ArticlesListState.Error)
        }
    }
}