package com.ptk.ptk_news.ui.ui_resource.navigation

sealed class Routes(val route: String) {
    data object NewsFeedScreen : Routes("/news_feed_screen")
    data object ArticlesScreen : Routes("/articles_screen")
    data object DetailScreen : Routes("/detail_screen")
    data object SettingScreen : Routes("/setting_screen")
    data object ProfileScreen : Routes("/profile_screen")
}