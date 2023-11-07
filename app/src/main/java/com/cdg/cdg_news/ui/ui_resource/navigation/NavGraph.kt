package com.cdg.cdg_news.ui.ui_resource.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cdg.cdg_news.db.entity.ArticleEntity
import com.cdg.cdg_news.ui.screen.ArticlesScreen
import com.cdg.cdg_news.ui.screen.BookMarkScreen
import com.cdg.cdg_news.ui.screen.DetailScreen
import com.cdg.cdg_news.ui.screen.LandingScreen
import com.cdg.cdg_news.ui.screen.NewsFeedScreen
import com.cdg.cdg_news.ui.screen.ProfileScreen
import com.cdg.cdg_news.ui.screen.SplashScreen

@Composable
fun NavGraph(
    scaffoldPaddingValue: Float,
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.padding(bottom = scaffoldPaddingValue.dp),
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ) {

        composable(route = Routes.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(route = Routes.LandingScreen.route) {
            LandingScreen(navController)
        }

        composable(route = Routes.NewsFeedScreen.route) {
            NewsFeedScreen(navController)
        }
        composable(route = Routes.ArticlesScreen.route) {
            ArticlesScreen(navController)
        }
        composable(route = Routes.DetailScreen.route) {
            val article = it.arguments?.getParcelable<ArticleEntity>("article")!!

            DetailScreen(navController, article)
        }
        composable(route = Routes.BookMarkScreen.route) {
            BookMarkScreen(navController)
        }
        composable(route = Routes.ProfileScreen.route) {
            ProfileScreen(navController)
        }


    }
}