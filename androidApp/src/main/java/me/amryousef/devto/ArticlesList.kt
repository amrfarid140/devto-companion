package me.amryousef.devto

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import me.amryousef.devto.presentation.ArticlesListState
import me.amryousef.devto.presentation.ArticlesListViewModel


@Composable
fun ArticlesListContainer(viewModel: ArticlesListViewModel, flow: Flow<ArticlesListState>) {
    val state = flow.collectAsState(initial = ArticlesListState.Loading)
    val listState = rememberLazyListState()
    return when (val stateValue = state.value) {
        is ArticlesListState.Ready -> {
            if (listState.firstVisibleItemIndex == stateValue.data.size - 10) {
                viewModel.onLoadMore()
            }
            LazyColumnFor(
                items = stateValue.data,
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