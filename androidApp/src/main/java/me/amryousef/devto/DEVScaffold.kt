package me.amryousef.devto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import me.amryousef.devto.presentation.ArticlesListState
import me.amryousef.devto.scaffold.AppDrawer
import me.amryousef.devto.ui.DEVTheme
import androidx.compose.material.icons.filled.Menu as MenuIcon

@Composable
fun DEVScaffold(
    tags: List<ArticlesListState.Ready.TagState>,
    onTagClicked: (ArticlesListState.Ready.TagState) -> Unit,
    onLoadMoreTags: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val state = rememberScaffoldState()
    return DEVTheme {
        Scaffold(
            scaffoldState = state,
            drawerContent = {
                AppDrawer(tags, onTagClicked, onLoadMoreTags)
            },
            topBar = {
                TopAppBar(title = { Text("Articles") }, navigationIcon = {
                    if (tags.isEmpty()) {
                        CircularProgressIndicator()
                    } else {
                        IconButton(onClick = { state.drawerState.open() }) {
                            Icon(
                                Icons.Default.MenuIcon
                            )
                        }
                    }
                })
            },
            bodyContent = content
        )
    }
}