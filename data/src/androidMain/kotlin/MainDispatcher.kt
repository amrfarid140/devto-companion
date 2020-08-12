import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val MainDispatcher: CoroutineDispatcher
    get() = Dispatchers.Main
internal actual val BackgroundDispatcher: CoroutineDispatcher
  get() = Dispatchers.Default