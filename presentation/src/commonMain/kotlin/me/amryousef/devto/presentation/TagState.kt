package me.amryousef.devto.presentation

sealed class TagState {
    data class Tag(val id: Long, val name: String, val isSelected: Boolean): TagState()
    object Loading: TagState()
}