package com.ptk.ptk_news.ui.ui_resource.navigation

sealed class Routes(val route: String) {
    data object ArticleListScreen : Routes("/article_list_screen")
    data object HeadlinesScreen : Routes("/headlines_screen")
    data object SettingScreen : Routes("/setting_screen")
    data object ProfileScreen : Routes("/profile_screen")
}