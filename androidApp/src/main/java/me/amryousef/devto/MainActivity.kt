package me.amryousef.devto

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.Scaffold
import androidx.ui.tooling.preview.Preview
import api.KtorArticlesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.amryousef.devto.ui.DEVTheme
import sample.Platform
import sample.Sample

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            val x = KtorArticlesApi("BNJUyn8jhYZoiihKgnwiT7fW").getArticles()
            Log.v("MainActivity", x.toString())
        }

        setContent {
            DEVTheme {
                Scaffold() {
                    Greeting(Platform.name())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DEVTheme {
        Scaffold() {
            Greeting("Android")
        }
    }
}