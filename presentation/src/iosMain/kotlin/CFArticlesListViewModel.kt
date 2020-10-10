package me.amryousef.devto.presentation

import kotlinx.coroutines.Dispatchers

class CFArticlesListViewModel: ArticlesListViewModel by ArticlesListViewModelImpl(Dispatchers.Main)