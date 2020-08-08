package me.amryousef.devto

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import api.model.Article
import presentation.ArticlesListState
import presentation.ArticlesListViewModel


@Composable
fun ArticlesListContainer(viewModel: ArticlesListViewModel) {
    val state = viewModel.state.collectAsState(initial = ArticlesListState.Started)
    val stateValue = state.value
    return when (stateValue) {
        is ArticlesListState.Ready -> LazyColumnFor(items = stateValue.data) {
            Box(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                    top = 12.dp
                )
            ) {
                ArticleListItem(article = it)
            }
        }
        else -> Text(text = "Not Ready")
    }
}

@Composable
fun ArticleListItem(article: Article) {
    Card (modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            remoteImage(imageUrl = article.coverImageUrl ?: "")
        }
    }
}