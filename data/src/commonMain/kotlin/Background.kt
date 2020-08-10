import kotlinx.coroutines.CoroutineDispatcher

internal expect val MainDispatcher: CoroutineDispatcher
internal expect fun <OUTPUT> runBlocking(block: suspend () -> OUTPUT): OUTPUT

interface Executor  {
    fun <OUTPUT> executeInBackground(block: suspend () -> OUTPUT, completion: (OUTPUT) -> Unit)
}

lateinit var executor: Executor