package com.cdg.cdg_news.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cdg.cdg_news.R
import com.cdg.cdg_news.db.entity.ArticleEntity
import com.cdg.cdg_news.ui.ui_resource.composables.CommentBoxDialog
import com.cdg.cdg_news.ui.ui_resource.composables.ReactionBar
import com.cdg.cdg_news.ui.ui_resource.navigation.Routes
import com.cdg.cdg_news.ui.ui_resource.theme.Red
import com.cdg.cdg_news.util.navigate
import com.cdg.cdg_news.viewmodel.ArticlesViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch


//UIs
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookMarkScreen(
    navController: NavController,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),

    ) {
    val uiStates by articlesViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
        articlesViewModel.getBookMarkArticles()
    }


    if (uiStates.bookMarkArticles.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "There is no articles yet.",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = Red,
                modifier = Modifier.padding(16.sdp)
            )
        }
    } else {
        BookMarkScreenContent(
            uiStates.bookMarkArticles,
            navController,
            articlesViewModel,
        )


        if (uiStates.showLoadingDialog) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            }
        }
    }


    CommentBoxDialog(
        showDialog = uiStates.showCommentDialog,
        uiStates = uiStates,
        toggleCommentText = { articlesViewModel.toggleCommentText(it) },
        onPostComment = { articlesViewModel.postComment() },
        onDismissRequest = { articlesViewModel.toggleCommentBoxDialog(false, 0) })
}

@Composable
fun BookMarkScreenContent(
    articleList: List<ArticleEntity>,
    navController: NavController,
    articlesViewModel: ArticlesViewModel,
) {
    val scope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        BookMarkArticlesList(
            articleList,
            navController,
            articlesViewModel,
        )

    }
}

@Composable
fun ColumnScope.BookMarkArticlesList(
    articleList: List<ArticleEntity>,
    navController: NavController,
    articlesViewModel: ArticlesViewModel,
) {
    LazyColumn(
        modifier = Modifier
            .weight(1F)
            .padding(horizontal = 8.sdp)
    ) {
        items(articleList) {
            BookMarkArticleItem(
                it,
                navController,
                articlesViewModel,
            )

        }
    }
}

@Composable
fun BookMarkArticleItem(
    article: ArticleEntity,
    navController: NavController,
    articlesViewModel: ArticlesViewModel,
) {
    Spacer(modifier = Modifier.height(16.sdp))

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.sdp))
            .clickable {
                navController.navigate(
                    route = Routes.DetailScreen.route, args = bundleOf(
                        "article" to article
                    )
                )
            }
    ) {
        if (!article.urlToImage.isNullOrEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "ArticleImage",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.sdp))
                    .fillMaxWidth()
                    .height(200.sdp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "ArticleImage",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.sdp))
                    .fillMaxWidth()
                    .height(200.sdp)
            )
        }

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
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    ReactionBar(
        articleEntity = article,
        favOnClick = {
            scope.launch {
                article.isFav = !article.isFav
                articlesViewModel.updateIsFav(article)
            }
        },
        commentOnClick = {
            articlesViewModel.toggleCommentBoxDialog(true, article.id)

        },
        bookMarkOnClick = {
            scope.launch {
                if (!article.isBookMark) {
                    article.isBookMark = true
                    articlesViewModel.insertBookMark(article)
                } else {
                    article.isBookMark = false
                    articlesViewModel.removeBookMark(article)
                    articlesViewModel.getBookMarkArticles()
                }
            }
        },
        shareOnClick = {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${article.url}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        })
    Spacer(modifier = Modifier.height(16.sdp))
    Divider()
}




