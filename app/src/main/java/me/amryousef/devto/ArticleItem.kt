package me.amryousef.devto


import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import me.amryousef.devto.presentation.State

data class ArticleItem(private val articleState: State.Ready.ArticleState) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    override fun getLayout() = R.layout.artice_row
}