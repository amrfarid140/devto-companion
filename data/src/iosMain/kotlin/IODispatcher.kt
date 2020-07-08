import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

internal actual val IODispatcher: CoroutineDispatcher = NsQueueDispatcher(
    dispatch_get_main_queue()
)

internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}