package presentation

import IODispatcher
import MainDispatcher
import api.KtorArticlesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.ThreadLocal

sealed class ArticlesListAction {
    object LoadData: ArticlesListAction()
}

sealed class ArticlesListState {
    object Loading: ArticlesListState()
    object Error: ArticlesListState()
    data class Ready(val articles: List<ArticlesListStateItem>): ArticlesListState()
}

data class ArticlesListStateItem(
    val name: String,
    val author: String
)

class ArticlesListViewModel: CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = MainDispatcher + job

    private val api by lazy { KtorArticlesApi("BNJUyn8jhYZoiihKgnwiT7fW") }

    private val stateChannel = ConflatedBroadcastChannel<ArticlesListState>(ArticlesListState.Loading)

    fun loadData() = launch {
        try {
            if (!stateChannel.isClosedForSend) {
                stateChannel.offer(ArticlesListState.Loading)
                val articles = api.getArticles()
                stateChannel.offer(ArticlesListState.Ready(articles.map {
                    ArticlesListStateItem(
                            name = it.title,
                            author = it.user.name
                    )
                }))
            }
        }catch (cancellation: CancellationException) {

        } catch (exception: Exception) {
            print(exception)
            stateChannel.offer(ArticlesListState.Error)
        }
    }

    fun watchState(onState: (ArticlesListState) -> Unit) = launch {
        stateChannel.asFlow().collect {
            onState(it)
        }
    }

    fun close() {
        job.cancelChildren()
        stateChannel.close()
    }
}