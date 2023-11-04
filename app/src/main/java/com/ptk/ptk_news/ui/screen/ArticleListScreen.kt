package com.ptk.ptk_news.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import com.ptk.ptk_news.viewmodel.ArticleListViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//UIs
@Composable
fun ArticleListScreen(
    navController: NavController,
    articleListViewModel: ArticleListViewModel = hiltViewModel(),

    ) {
    val uiStates by articleListViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
        articleListViewModel.getNewsFeed()
    }
    ArticleListScreenContent(navController, uiStates.newsFeedList)


}

@Composable
fun ArticleListScreenContent(navController: NavController, articleList: List<ArticlesItem>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "PTKNews",
            fontSize = 16.ssp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 12.sdp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        ArticleList(articleList)
    }

}

@Composable
fun ColumnScope.ArticleList(articleList: List<ArticlesItem>) {
    LazyColumn(modifier = Modifier
        .weight(1F)
        .padding(16.sdp)) {
        items(articleList) {
            ArticleListItem(it)
        }
    }
}

@Composable
fun ArticleListItem(article: ArticlesItem) {

    Row {
        Spacer(modifier = Modifier.width(8.sdp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://i.kinja-img.com/image/upload/c_fill,h_675,pg_1,q_80,w_1200/03244027a0f8e5f2f09fa2e3522d2495.png")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "ArticleImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.sdp).clip(RoundedCornerShape(16.sdp))
        )
        Spacer(modifier = Modifier.width(8.sdp))
        Text(
            text = article.title ?: "-",
            fontSize = 16.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "By ${article.author}",
            fontSize = 12.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }

}

