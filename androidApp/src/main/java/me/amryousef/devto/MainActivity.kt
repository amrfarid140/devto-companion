package me.amryousef.devto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.amryousef.devto.presentation.ArticlesListState
import me.amryousef.devto.presentation.ArticlesListViewModelImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ArticlesListViewModelImpl(lifecycleScope.coroutineContext)
        setContent {
            val navController = rememberNavController()
            val state = viewModel.state.collectAsState(initial = ArticlesListState.Loading)
            DEVScaffold(
                tags = (state.value as? ArticlesListState.Ready)?.tags ?: emptyList(),
                onTagClicked = { viewModel.onTagSelected(it) }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Route.LIST.uri,
                ) {
                    composable(Route.LIST.uri) {
                        ArticlesListContainer(
                            navController = navController,
                            state = state.value,
                            onLoadMore = { viewModel.onLoadMore() }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
