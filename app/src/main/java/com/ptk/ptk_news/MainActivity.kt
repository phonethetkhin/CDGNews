package com.ptk.ptk_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ptk.ptk_news.ui.ui_resource.navigation.NavGraph
import com.ptk.ptk_news.ui.ui_resource.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MainComposable()
            }
        }
    }
}

@Composable
fun MainComposable() {
    val navController = rememberNavController()

    NavGraph(
        navController
    )
}