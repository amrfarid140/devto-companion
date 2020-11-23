package me.amryousef.devto.presentation

data class PageState<out DATA>(val page: Int, val items: List<DATA>)