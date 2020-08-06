package me.amryousef.devto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnItems
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import me.amryousef.devto.ui.DEVTheme
import presentation.ArticlesListState
import presentation.ArticlesListViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DEVTheme {
                Scaffold {
                    ArticlesListContainer(ArticlesListViewModel(CoroutineExecutor()))
                }
            }
        }
    }
}

@Composable
fun ArticlesListContainer(viewModel: ArticlesListViewModel) {
    val state = viewModel.state.collectAsState(initial = ArticlesListState.Started)
    val stateValue = state.value
    return when(stateValue) {
        is ArticlesListState.Ready -> LazyColumnFor(items = stateValue.data) {
            Text(text = it.title)
        }
        else -> Text(text = "Not Ready")
    }
}
