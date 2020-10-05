package me.amryousef.devto.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

class CFArticlesListViewModel: CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job = Job()
    private val viewModel = ArticlesListViewModel(this)

    fun onChanged(consumer: (ArticlesListState) -> Unit) = viewModel.onChanged(consumer)

    fun cancel() {
        job.cancelChildren()
    }
}