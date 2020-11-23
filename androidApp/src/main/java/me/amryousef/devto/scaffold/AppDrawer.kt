package me.amryousef.devto.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.amryousef.devto.presentation.TagState

@Composable
fun AppDrawer(
    tags: List<TagState>,
    onTagClick: (TagState.Tag) -> Unit,
    onLoadMore: () -> Unit
) {
    LazyColumnFor(
        items = tags,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        if (it is TagState.Tag) {
            DrawerTag(tag = it, onClick = { onTagClick(it) })
        } else {
            CircularProgressIndicator()
        }
        Spacer(modifier = Modifier.height(1.dp))

        if (tags.indexOf(it) == tags.size - 1) {
            TextButton(
                onClick = onLoadMore,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Load More",
                    style = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
            }
        }
    }
}

@Composable
fun DrawerTag(
    tag: TagState.Tag,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                color = if (tag.isSelected) {
                    MaterialTheme.colors.secondary
                } else {
                    MaterialTheme.colors.primary
                },
            )
            .padding(18.dp)
    ) {
        Text(
            text = "#${tag.name}",
            textAlign = TextAlign.Left,
            style = MaterialTheme
                .typography
                .button
                .copy(
                    textAlign = TextAlign.Center,
                    color = if (tag.isSelected) {
                        MaterialTheme.colors.onSecondary
                    } else {
                        MaterialTheme.colors.onPrimary
                    }
                ),
        )
    }

}