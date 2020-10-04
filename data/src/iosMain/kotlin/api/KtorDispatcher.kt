package api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val KtorDispatcher: CoroutineDispatcher = Dispatchers.Default