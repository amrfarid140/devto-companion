package me.amryousef.devto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import me.amryousef.devto.presentation.ArticlesListState
import me.amryousef.devto.presentation.ArticlesListViewModel
import me.amryousef.devto.presentation.ArticlesListViewModelImpl
import me.amryousef.devto.ui.DEVTheme
import me.amryousef.devto.ui.lightTextOnBackground

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
            DEVScaffold {
                ArticlesListContainer(viewModel, stateFlow)
            }
        }
    }
}

@Composable
fun DEVScaffold(content: @Composable() (PaddingValues) -> Unit) {
    return DEVTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            backgroundColor = lightTextOnBackground,
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.size(width = 64.dp, height = 48.dp),
                            padding = 8.dp
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                asset = vectorResource(id = R.drawable.ic_dev),
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        OutlinedTextField(
                            value = TextFieldValue(""),
                            onValueChange = {},
                            label = { Text(text = "Search...") },
                            placeholder = { Text(text = "Search...") },
                            modifier = Modifier.fillMaxWidth().then(
                                Modifier.padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    bottom = 8.dp
                                )
                            )
                        )
                    }
                }
            },
            bodyContent = content
        )
    }
}
