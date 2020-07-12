import kotlinx.coroutines.CoroutineDispatcher
import kotlin.native.concurrent.ThreadLocal

internal expect val IODispatcher: CoroutineDispatcher

internal expect val MainDispatcher: CoroutineDispatcher