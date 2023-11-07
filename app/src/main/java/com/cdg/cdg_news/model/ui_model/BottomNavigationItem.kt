package com.cdg.cdg_news.model.ui_model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.ui.graphics.vector.ImageVector
import com.cdg.cdg_news.ui.ui_resource.navigation.Routes

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
)

fun bottomNavigationItems(): List<BottomNavigationItem> = listOf(
    BottomNavigationItem(
        title = "NewsFeed",
        icon = Icons.Filled.Newspaper,
        route = Routes.NewsFeedScreen.route,

        ),
    BottomNavigationItem(
        title = "Articles",
        icon = Icons.Filled.Article,
        route = Routes.ArticlesScreen.route,
    ),

    BottomNavigationItem(
        title = "Bookmarks",
        icon = Icons.Filled.Bookmark,
        route = Routes.BookMarkScreen.route,

        ),
    BottomNavigationItem(
        title = "Profile",
        icon = Icons.Filled.AccountCircle,
        route = Routes.ProfileScreen.route,

        ),
)
