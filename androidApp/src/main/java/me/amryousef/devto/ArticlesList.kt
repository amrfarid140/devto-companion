package me.amryousef.devto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.amryousef.devto.presentation.ArticlesListState


@Composable
fun ArticlesListContainer(
    navController: NavController,
    state: ArticlesListState,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    return when (state) {
        is ArticlesListState.Ready -> {
            if (listState.firstVisibleItemIndex == state.data.size - 10) {
                onLoadMore()
            }
            LazyColumnFor(
                items = state.data,
                state = listState,
                modifier = Modifier
            ) { item ->
                Box(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 12.dp,
                        top = 12.dp
                    )
                ) { ArticleListItem(article = item) }
            }
        }
        is ArticlesListState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
        }
        is ArticlesListState.Error -> Text(text = "error")
    }
}

@Composable
fun ArticleListItem(article: ArticlesListState.Ready.ArticleState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            remoteImage(imageUrl = article.coverImageUrl)
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = article.title, style = MaterialTheme.typography.h6)
                Text(text = article.userName, style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}