package me.amryousef.devto

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.amryousef.devto.presentation.ArticlesListState
import me.amryousef.devto.presentation.ArticlesListViewModel


@Composable
fun ArticlesListContainer(viewModel: ArticlesListViewModel) {
    val state = remember { mutableStateOf<ArticlesListState>(ArticlesListState.Loading) }
    viewModel.onChanged {
        state.value = it
    }
    return when (val stateValue = state.value) {
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
        is ArticlesListState.Loading -> CircularProgressIndicator()
        is ArticlesListState.Error -> Text(text = "error")
    }
}

@Composable
fun ArticleListItem(article: ArticlesListState.Ready.ArticleState) {
    Card (modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            remoteImage(imageUrl = article.coverImageUrl)
        }
    }
}