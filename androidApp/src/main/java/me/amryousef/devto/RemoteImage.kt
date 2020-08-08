package me.amryousef.devto

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.LoadRequest

private sealed class RemoteImageState {
    object Loading: RemoteImageState()
    data class Ready(val bitmap: Bitmap): RemoteImageState()
}

@Composable
fun remoteImage(imageUrl: String) {
    return when(val state = imageLoader(imageUrl = imageUrl)) {
        RemoteImageState.Loading -> CircularProgressIndicator()
        is RemoteImageState.Ready -> Image(
            asset = state.bitmap.asImageAsset(),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun imageLoader(imageUrl: String): RemoteImageState {
    var state by remember { mutableStateOf<RemoteImageState>(RemoteImageState.Loading) }
    val context = ContextAmbient.current
    val request = LoadRequest.Builder(context)
        .data(imageUrl)
        .target { drawable ->
            val bitmap = drawable.toBitmap()
            state = RemoteImageState.Ready(bitmap)
        }
        .build()
    Coil.imageLoader(context).execute(request)
    return state
}
