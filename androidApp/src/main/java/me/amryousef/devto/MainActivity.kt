package me.amryousef.devto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import me.amryousef.devto.presentation.ArticlesListViewModelImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ArticlesListViewModelImpl(lifecycleScope.coroutineContext)
        val stateFlow = callbackFlow {
            viewModel.onChanged {
                this.offer(it)
            }
            awaitClose {  }
        }
        setContent {
            val navController = rememberNavController()
            DEVScaffold {
                NavHost(
                    navController = navController,
                    startDestination = Route.LIST.uri,
                ) {
                    composable(Route.LIST.uri) {
                        ArticlesListContainer(
                            navController = navController,
                            viewModel = viewModel,
                            flow = stateFlow
                        )
                    }
                }
            }
        }
    }
}
