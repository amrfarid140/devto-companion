import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val IODispatcher: CoroutineDispatcher = Dispatchers.IO
internal actual val MainDispatcher: CoroutineDispatcher = Dispatchers.Main