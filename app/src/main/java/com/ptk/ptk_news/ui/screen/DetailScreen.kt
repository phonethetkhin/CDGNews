package com.ptk.ptk_news.ui.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.ptk_news.R
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.util.getConvertDate
import com.ptk.ptk_news.viewmodel.ArticlesViewModel
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch


//UIs
@Composable
fun DetailScreen(
    navController: NavController,
    articleEntity: ArticleEntity,
    viewModel: NewsFeedViewModel = hiltViewModel(),
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
) {
    val uiStates by viewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
        viewModel.setArticleEntity(articleEntity)
    }
    if (uiStates.articleEntity != null) {
        DetailScreenContent(uiStates.articleEntity!!, navController, viewModel, articlesViewModel)
    }


}

@Composable
fun DetailScreenContent(
    articleEntity: ArticleEntity,
    navController: NavController,
    viewModel: NewsFeedViewModel,
    articlesViewModel: ArticlesViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopDetailBox(articleEntity = articleEntity, navController = navController)
        TitleRow(
            articleEntity = articleEntity,
            viewModel = viewModel,
            articlesViewModel = articlesViewModel
        )
        Spacer(modifier = Modifier.height(16.sdp))
        AuthorRow(articleEntity = articleEntity)
        Spacer(modifier = Modifier.height(16.sdp))

        Text(
            articleEntity.description ?: "-",
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            modifier = Modifier.padding(8.sdp)
        )
        val uriHandler = LocalUriHandler.current

        if (!articleEntity.url.isNullOrEmpty()) {
            TextButton(
                onClick = {
                    uriHandler.openUri(articleEntity.url)
                }, modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.sdp)
            ) {
                Text(
                    text = "Read More",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.primary
                )

            }
        }

    }
}

@Composable
fun TopDetailBox(articleEntity: ArticleEntity, navController: NavController) {
    val context = LocalContext.current
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(articleEntity.urlToImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "ArticleImage",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.sdp)
                .clip(RoundedCornerShape(4.sdp))
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "BackIcon",
                modifier = Modifier
                    .padding(16.sdp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Black,
                            radius = this.size.minDimension,
                            alpha = 0.25F
                        )
                    }
                    .clickable { navController.navigateUp() },
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "ShareIcon",
                modifier = Modifier
                    .padding(16.sdp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Black,
                            radius = this.size.minDimension,
                            alpha = 0.25F
                        )
                    }
                    .clickable {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "${articleEntity.url}")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                tint = Color.White
            )
        }
    }
}

@Composable
fun TitleRow(
    articleEntity: ArticleEntity,
    viewModel: NewsFeedViewModel,
    articlesViewModel: ArticlesViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        val scope = rememberCoroutineScope()
        Text(
            articleEntity.title ?: "-",
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1F)
                .padding(start = 8.sdp, top = 8.sdp)
        )
        Column {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "FavIcon",
                modifier = Modifier

                    .padding(16.sdp)
                    .size(15.sdp)
                    .clickable {
                        scope.launch {
                            articleEntity.isFav = !articleEntity.isFav
                            viewModel.updateIsFav(articleEntity)
                        }
                    }
                    .drawBehind {
                        drawCircle(
                            color = Color.Black,
                            radius = this.size.minDimension,
                            alpha = 0.25F
                        )
                    },
                tint = if (articleEntity.isFav) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
            )
            Icon(
                imageVector = Icons.Filled.Bookmark,
                contentDescription = "BookMarkIcon",
                modifier = Modifier

                    .padding(16.sdp)
                    .size(15.sdp)
                    .clickable {
                        scope.launch {
                            if (!articleEntity.isBookMark) {
                                articleEntity.isBookMark = true
                                viewModel.insertBookMark(articleEntity)
                            } else {
                                articleEntity.isBookMark = false
                                viewModel.removeBookMark(articleEntity)
                                articlesViewModel.getBookMarkArticles()

                            }
                        }
                    }
                    .drawBehind {
                        drawCircle(
                            color = Color.Black,
                            radius = this.size.minDimension,
                            alpha = 0.25F
                        )
                    },
                tint = if (articleEntity.isBookMark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun AuthorRow(articleEntity: ArticleEntity) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        var authorName = articleEntity.author ?: "-"

        if (authorName.isNotEmpty()) {
            if (authorName.contains("<a")) {
                authorName = authorName.split("<")[0]
            }
            Text(
                "By $authorName",
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1F)

                    .padding(start = 8.sdp)
                    .clip(
                        RoundedCornerShape(8.sdp)
                    )
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.sdp)
            )
        }
        val date =
            articleEntity.publishedAt?.getConvertDate(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "dd/MM/yyyy"
            )
                ?: "-"
        Text(
            date,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            modifier = Modifier
                .padding(8.sdp),
            textAlign = TextAlign.End
        )
    }
}

