import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class FlowObserver<T>(private val flow: Flow<T>) {
    fun watch(block: (data: T) -> Unit) = runBlocking {
        withContext(Dispatchers.Main) {
            flow.collect {
                block(it)
            }
        }
    }
}