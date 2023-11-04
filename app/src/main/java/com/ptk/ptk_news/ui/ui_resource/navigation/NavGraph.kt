package com.ptk.ptk_news.ui.ui_resource.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ptk.ptk_news.ui.screen.ArticleListScreen
import com.ptk.ptk_news.ui.screen.HeadLinesScreen
import com.ptk.ptk_news.ui.screen.ProfileScreen
import com.ptk.ptk_news.ui.screen.SettingScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Routes.ArticleListScreen.route) {
        composable(route = Routes.ArticleListScreen.route) {
            ArticleListScreen(navController)
        }
        composable(route = Routes.HeadlinesScreen.route) {
            HeadLinesScreen(navController)
        }
        composable(route = Routes.SettingScreen.route) {
            SettingScreen(navController)
        }
        composable(route = Routes.ProfileScreen.route) {
            ProfileScreen(navController)
        }


    }
}