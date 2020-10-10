package me.amryousef.devto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
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
