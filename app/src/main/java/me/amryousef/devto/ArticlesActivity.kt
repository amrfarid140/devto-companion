package me.amryousef.devto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_articles.*
import me.amryousef.devto.presentation.ArticlesViewModel
import me.amryousef.devto.presentation.State
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArticlesActivity : AppCompatActivity() {


    private val viewModel by viewModel<ArticlesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        val adapter = GroupAdapter<GroupieViewHolder>()
        articles_list.apply {
            layoutManager = LinearLayoutManager(this@ArticlesActivity)
            this.adapter = adapter
        }
        viewModel.connect(this, { state ->
            (state as? State.Ready)?.let { readyState ->
                adapter.updateAsync(readyState.articles.map { ArticleItem(it) })
            }
        }, {})
    }
}
