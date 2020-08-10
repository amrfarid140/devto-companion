import kotlinx.coroutines.CoroutineDispatcher

internal expect val MainDispatcher: CoroutineDispatcher

interface Executor  {
    fun <OUTPUT> executeInBackground(block: suspend () -> OUTPUT, completion: (OUTPUT) -> Unit)
}

lateinit var executor: Executor