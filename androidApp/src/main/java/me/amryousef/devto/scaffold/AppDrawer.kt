package me.amryousef.devto.scaffold

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.amryousef.devto.presentation.ArticlesListState

@Composable
fun AppDrawer(
    tags: List<ArticlesListState.Ready.TagState>,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        DrawerTag(text = "Test Me")
    }
}

@Composable
fun DrawerTag(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Left,
        style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {  })
            .padding(vertical = 16.dp, horizontal = 12.dp)
    )
}