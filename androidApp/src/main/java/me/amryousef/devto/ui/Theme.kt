package me.amryousef.devto.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    background = Color(0xFFd2d6da),
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    secondary = Color.Black,
    secondaryVariant = Color.Black,
    onSecondary = Color.White,
    primary = Color.White,
    onPrimary = Color.Black,
    primaryVariant = Color(0xFFd2d6da),
    error = Color.Red,
    onError = Color.White
)

@Composable
fun DEVTheme(content: @Composable() () -> Unit) {
  MaterialTheme(
      colors = LightColorPalette,
      typography = typography,
      shapes = shapes,
      content = content
  )
}