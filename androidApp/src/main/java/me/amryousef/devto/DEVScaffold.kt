package me.amryousef.devto

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import me.amryousef.devto.presentation.ArticlesListState
import me.amryousef.devto.scaffold.AppDrawer
import me.amryousef.devto.ui.DEVTheme
import androidx.compose.material.icons.filled.Menu as MenuIcon

@Composable
fun DEVScaffold(
    tags: List<ArticlesListState.Ready.TagState>,
    onTagClicked: (ArticlesListState.Ready.TagState) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val state = rememberScaffoldState()
    return DEVTheme {
        Scaffold(
            scaffoldState = state,
            drawerContent = {
                AppDrawer(tags, onTagClicked)
            },
            topBar = {
                TopAppBar(title = { Text("Articles") }, navigationIcon = {
                    IconButton(onClick = { state.drawerState.open() }) {
                        Icon(
                            Icons.Default.MenuIcon
                        )
                    }
                })
            },
            bodyContent = content
        )
    }
}