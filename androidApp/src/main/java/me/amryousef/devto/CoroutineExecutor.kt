package me.amryousef.devto

import Executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CoroutineExecutor : CoroutineScope, Executor {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun <OUTPUT> executeInBackground(block: () -> OUTPUT, completion: (OUTPUT) -> Unit) {
        launch {
            val result = block()
            withContext(Dispatchers.Main) {
                completion(result)
            }
        }
    }

}