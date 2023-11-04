package com.ptk.ptk_news.model.ui_model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ptk.ptk_news.ui.ui_resource.navigation.Routes

data class BottomNavigationItem(
    val title:String,
    val icon:ImageVector,
    val route:String,
)

fun bottomNavigationItems() : List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = "Article",
            icon = Icons.Filled.Article,
            route = Routes.ArticleListScreen.route,
        ),
        BottomNavigationItem(
            title = "Headlines",
            icon = Icons.Filled.Newspaper,
            route = Routes.HeadlinesScreen.route,

            ),
        BottomNavigationItem(
            title = "Setting",
            icon = Icons.Filled.Settings,
            route = Routes.SettingScreen.route,

        ),
        BottomNavigationItem(
            title = "Profile",
            icon = Icons.Filled.AccountCircle,
            route = Routes.ProfileScreen.route,

        ),
    )
}