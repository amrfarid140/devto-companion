package me.amryousef.devto

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.amryousef.devto.ui.DEVTheme
import androidx.compose.material.icons.filled.Menu as MenuIcon

@Composable
fun DEVScaffold(content: @Composable (PaddingValues) -> Unit) {
    val state = rememberScaffoldState()
    return DEVTheme {
        Scaffold(
            scaffoldState = state,
            drawerContent = {
                Column {
                    TextButton(onClick = {}) {
                        Text(
                            text = "Tooot",
                            style = MaterialTheme.typography.button.copy(color = Color.Black)
                        )
                    }
                }
            },
            topBar = {
                TopAppBar(title = { Text("Articles") }, navigationIcon = {
                    IconButton(onClick = { state.drawerState.open() }) {
                        Icon(
                            Icons.Default.MenuIcon,
                            tint = Color.Black
                        )
                    }
                })
            },
            bodyContent = content
        )
    }
}