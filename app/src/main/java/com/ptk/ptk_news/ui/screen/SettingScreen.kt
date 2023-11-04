package com.ptk.ptk_news.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ptk.ptk_news.model.dto.response.ArticlesItem
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel


//UIs
@Composable
fun SettingScreen(
    navController: NavController,
    newsFeedViewModel: NewsFeedViewModel = hiltViewModel(),

    ) {
    val uiStates by newsFeedViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
        newsFeedViewModel.getNewsFeed()
    }
    SettingScreenContent(navController, uiStates.newsFeedList)


}

@Composable
fun SettingScreenContent(navController: NavController, articleList: List<ArticlesItem>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Setting")

    }

}

