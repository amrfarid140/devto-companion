import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

internal actual fun <OUTPUT> runBlocking(block: suspend  () -> OUTPUT): OUTPUT {
    return kotlinx.coroutines.runBlocking { block() }
}

internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}

internal actual val MainDispatcher: CoroutineDispatcher =
    NsQueueDispatcher(dispatch_get_main_queue())