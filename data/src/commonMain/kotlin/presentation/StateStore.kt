package presentation

import IODispatcher
import api.KtorArticlesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

@ThreadLocal
private val reducer: suspend (stateChannel: SendChannel<ArticlesListState>,action: ArticlesListAction) -> Unit = {
    stateChannel, action ->
    when(action) {
        is ArticlesListAction.LoadData -> {
            stateChannel.offer(ArticlesListState.Loading)
            try {
                stateChannel.offer(ArticlesListState.Ready(
                    KtorArticlesApi("BNJUyn8jhYZoiihKgnwiT7fW").getArticles().map {
                        ArticlesListStateItem(
                            name = it.title,
                            author = it.user.name
                        )
                    }
                ))
            } catch (exception: Exception) {
                stateChannel.offer(ArticlesListState.Error)
            }
        }
    }
}

@Suppress("EXPERIMENTAL_API_USAGE")
@ThreadLocal
object StateStore: CoroutineScope {

    private val stateChannel = ConflatedBroadcastChannel<ArticlesListState>()

    fun dispatch(action: ArticlesListAction) {
        launch { reduce(action) }
    }

    private suspend fun reduce(action: ArticlesListAction) = withContext(IODispatcher){
        reducer(stateChannel, action)
    }

    fun state() = stateChannel
        .asFlow()
        .flowOn(Dispatchers.Main)
        .onStart {
            dispatch(ArticlesListAction.LoadData)
            emit(ArticlesListState.Loading)
        }

    override val coroutineContext = Dispatchers.Main
}