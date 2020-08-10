import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val MainDispatcher: CoroutineDispatcher
    get() = Dispatchers.Main

internal actual fun <OUTPUT> runBlocking(block: suspend () -> OUTPUT): OUTPUT =
    kotlinx.coroutines.runBlocking { block() }
