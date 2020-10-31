package me.amryousef.devto.presentation

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CFArticlesListViewModel :
    ArticlesListViewModel by ArticlesListViewModelImpl(Dispatchers.Main) {
    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)

    fun onChanged(consumer: (ArticlesListState) -> Unit) {
        scope.launch {
            state.collect {
                consumer(it)
            }
        }
    }

    override fun cancel() {
        job.cancelChildren()
    }
}