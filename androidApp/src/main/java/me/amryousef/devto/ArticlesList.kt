package me.amryousef.devto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.amryousef.devto.presentation.ArticleState
import me.amryousef.devto.presentation.ArticlesListState

@Composable
fun ArticlesListContainer(
    navController: NavController,
    state: ArticlesListState,
    onLoadMore: () -> Unit
) {
    return when (state) {
        is ArticlesListState.Ready -> {
            LazyColumnFor(
                items = state.articlesState.items,
                modifier = Modifier
            ) { item ->
                Box(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 12.dp,
                        top = 12.dp
                    )
                ) {
                    if (item is ArticleState.Article) {
                        ArticleListItem(article = item)
                    } else {
                        CircularProgressIndicator()
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))

                if (state.articlesState.items.indexOf(item) == state.articlesState.items.size - 1) {
                    TextButton(
                        onClick = onLoadMore,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Load more",
                            style = MaterialTheme.typography.button.copy(
                                color = MaterialTheme.colors.onPrimary
                            )
                        )
                    }
                }
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
fun ArticleListItem(article: ArticleState.Article) {
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