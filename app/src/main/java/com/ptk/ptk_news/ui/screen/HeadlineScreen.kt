package com.ptk.ptk_news.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.ptk.ptk_news.ui.ui_resource.theme.LightGreen
import com.ptk.ptk_news.viewmodel.ArticleListViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//UIs
@Composable
fun HeadLinesScreen(
    navController: NavController,
    articleListViewModel: ArticleListViewModel = hiltViewModel(),

    ) {
    val uiStates by articleListViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
        articleListViewModel.getHeadlines()
    }
    HeadlinesScreenContent(navController, uiStates.headLinesList)


}

@Composable
fun HeadlinesScreenContent(navController: NavController, articleList: List<ArticlesItem>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Headlines",
            fontSize = 16.ssp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 12.sdp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        HeadlinesList(articleList = articleList)
    }

}

@Composable
fun ColumnScope.HeadlinesList(articleList: List<ArticlesItem>) {
    LazyColumn(
        modifier = Modifier
            .weight(1F)
            .padding(8.sdp)
    ) {
        items(articleList) {
            HeadlinesListItem(it)
            Divider()
        }
    }
}

@Composable
fun HeadlinesListItem(article: ArticlesItem) {

    Box(modifier = Modifier.padding(vertical = 8.sdp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "ArticleImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.sdp)
                .clip(RoundedCornerShape(4.sdp))
        )
        Surface(
            modifier = Modifier.align(Alignment.BottomStart),
            color = Color.Black.copy(alpha = 0.6f)
        ) {
            Text(
                text = article.title ?: "-",
                fontSize = 12.ssp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.sdp)
                )
        }

    }

}

