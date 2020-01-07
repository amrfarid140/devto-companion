package me.amryousef.devto

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import me.amryousef.devto.presentation.ArticlesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesActivity : AppCompatActivity() {

    private val viewModel by viewModel<ArticlesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        viewModel.connect(this, {
            Log.v(ArticlesViewModel::class.java.simpleName, it.toString())
        }, {})
    }
}
