package me.amryousef.devto

import android.os.Bundle
import me.amryousef.devto.R
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import me.amryousef.devto.ui.*
import presentation.ArticlesListState
import presentation.ArticlesListViewModel

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      DEVScaffold {
        ArticlesListContainer(ArticlesListViewModel(CoroutineExecutor()))
      }
    }
  }
}

@Composable
fun DEVScaffold(content: @Composable() (InnerPadding) -> Unit) {
  return DEVTheme {
    Scaffold(
        topBar = {
          TopAppBar {
            Row(
                verticalGravity = Alignment.CenterVertically,
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

@Composable
fun ArticlesListContainer(viewModel: ArticlesListViewModel) {
  val state = viewModel.state.collectAsState(initial = ArticlesListState.Started)
  val stateValue = state.value
  return when (stateValue) {
    is ArticlesListState.Ready -> LazyColumnFor(items = stateValue.data) {
      Text(text = it.title)
    }
    else -> Text(text = "Not Ready")
  }
}
