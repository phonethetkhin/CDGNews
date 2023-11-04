@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.ptk_news.R
import com.ptk.ptk_news.model.dto.response.ArticlesItem
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//UIs
@Composable
fun ArticlesScreen(
    navController: NavController,
    newsFeedViewModel: NewsFeedViewModel = hiltViewModel(),

    ) {
    val uiStates by newsFeedViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
        newsFeedViewModel.getArticles()
    }
    ArticlesScreenContent(navController, uiStates.newsFeedList)


}

@Composable
fun ArticlesScreenContent(navController: NavController, articleList: List<ArticlesItem>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            "Articles",
            fontSize = 16.ssp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 12.sdp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.sdp))

        ArticleList(articleList)
    }

}

@Composable
fun ColumnScope.ArticleList(articleList: List<ArticlesItem>) {
    LazyColumn(
        modifier = Modifier
            .weight(1F)
            .padding(8.sdp)
    ) {
        items(articleList) {
            ArticleListItem(it)
            Divider()
        }
    }
}

@Composable
fun ArticleListItem(article: ArticlesItem) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.sdp)
    ) {
        Spacer(modifier = Modifier.width(8.sdp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "ArticleImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.sdp)
                .clip(RoundedCornerShape(4.sdp))
        )
        Spacer(modifier = Modifier.width(8.sdp))
        Text(
            text = article.title ?: "-",
            fontSize = 11.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

    }

}

