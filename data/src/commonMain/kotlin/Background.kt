import kotlinx.coroutines.CoroutineDispatcher

internal expect val MainDispatcher: CoroutineDispatcher
internal expect fun <OUTPUT> runBlocking(block: suspend () -> OUTPUT): OUTPUT

interface Executor  {
    fun <OUTPUT> executeInBackground(block: suspend () -> OUTPUT, completion: (OUTPUT) -> Unit)
}

suspend fun invoke0(block: Any) = kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn<Any?> {
    (block as Function1<Any?, Any?>).invoke(it)
}

lateinit var executor: Executor