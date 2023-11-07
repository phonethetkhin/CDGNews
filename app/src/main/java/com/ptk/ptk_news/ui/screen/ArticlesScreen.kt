@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.ptk.ptk_news.R
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.ui.ui_resource.composables.CommentBoxDialog
import com.ptk.ptk_news.ui.ui_resource.composables.FilterSourceDialog
import com.ptk.ptk_news.ui.ui_resource.composables.NoConnectionDialog
import com.ptk.ptk_news.ui.ui_resource.composables.ReactionBar
import com.ptk.ptk_news.ui.ui_resource.composables.SearchView
import com.ptk.ptk_news.ui.ui_resource.navigation.Routes
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import com.ptk.ptk_news.util.navigate
import com.ptk.ptk_news.viewmodel.ArticlesViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


//UIs
@Composable
fun ArticlesScreen(
    navController: NavController,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),

    ) {
    val uiStates by articlesViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {

        articlesViewModel.getAllSourcesForArticle()
        articlesViewModel.getArticles()

    }
    ArticlesScreenContent(
        navController,
        uiStates,
        articlesViewModel,
    )

    val scope = rememberCoroutineScope()

    FilterSourceDialog(
        showDialog = uiStates.isShowFilterDialog,
        uiStates,
        articlesViewModel,
        onDismissRequest = {
            articlesViewModel.resetSelectedValueForArticle()
            articlesViewModel.toggleIsShowFilterSourceDialog(false)
        }) {
        scope.launch {
            articlesViewModel.savePreferredSettingForArticle()
            articlesViewModel.getArticles()
            articlesViewModel.toggleIsShowFilterSourceDialog(false)
        }
    }

    if (uiStates.showLoadingDialog) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }
    }

    CommentBoxDialog(
        showDialog = uiStates.showCommentDialog,
        uiStates = uiStates,
        toggleCommentText = { articlesViewModel.toggleCommentText(it) },
        onPostComment = { articlesViewModel.postComment() },
        onDismissRequest = { articlesViewModel.toggleCommentBoxDialog(false, 0) })

    NoConnectionDialog(
        showDialog = uiStates.isShowDisconnectedDialog,
        onDismissRequest = { articlesViewModel.toggleIsShowDCDialog(false) })
}

@Composable
fun ArticlesScreenContent(
    navController: NavController,
    uiStates: ArticleUIStates,
    viewModel: ArticlesViewModel,
) {
    val scope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.sdp)
        ) {

            SearchView(
                selectedText = uiStates.articleSearchText,
                onSearchValueChanged = viewModel::toggleSearchValueChange,
                modifier = Modifier.weight(1F)
            ) {
                scope.launch {
                    viewModel.getArticles()
                }
            }
            Spacer(modifier = Modifier.width(4.sdp))

            Icon(
                imageVector = if (uiStates.availableSources.none { it.selected }) Icons.Filled.FilterAltOff else Icons.Filled.FilterAlt,
                contentDescription = "FilterIcon",
                modifier = Modifier
                    .size(30.sdp)
                    .clickable {
                        viewModel.toggleIsShowFilterSourceDialog(true)
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }

        SortByRow(uiStates = uiStates, viewModel = viewModel, scope = scope)
        if (uiStates.articlesList.isEmpty() && uiStates.errorMessage.isNotEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .padding(8.sdp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    uiStates.errorMessage,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = Color.Black
                )
            }
        } else {
            ArticleList(
                navController,
                uiStates.articlesList,
                viewModel,
            )
        }
    }

}

@Composable
fun SortByRow(
    uiStates: ArticleUIStates,
    viewModel: ArticlesViewModel,
    scope: CoroutineScope
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.sdp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CustomSortByBox(
            color = if (uiStates.selectedSortBy == 1) MaterialTheme.colorScheme.secondary else Color.Transparent,
            text = "Relevancy",
            textColor =
            if (uiStates.selectedSortBy == 1) MaterialTheme.colorScheme.onSecondary else Color.Black
        ) {
            viewModel.toggleSortBy(1)
            scope.launch {
                viewModel.getArticles()
            }
        }

        CustomSortByBox(
            color = if (uiStates.selectedSortBy == 2) MaterialTheme.colorScheme.secondary else Color.Transparent,
            text = "Popularity",
            textColor =
            if (uiStates.selectedSortBy == 2) MaterialTheme.colorScheme.onSecondary else Color.Black
        ) {
            viewModel.toggleSortBy(2)
            scope.launch {
                viewModel.getArticles()
            }
        }

        CustomSortByBox(
            color = if (uiStates.selectedSortBy == 3) MaterialTheme.colorScheme.secondary else Color.Transparent,
            text = "PublishedAt",
            textColor =
            if (uiStates.selectedSortBy == 3) MaterialTheme.colorScheme.onSecondary else Color.Black
        ) {
            viewModel.toggleSortBy(3)
            scope.launch {
                viewModel.getArticles()
            }
        }
    }
}

@Composable
fun CustomSortByBox(color: Color, text: String, textColor: Color, onclick: () -> Unit) {
    Box(
        Modifier
            .clip(RoundedCornerShape(8.sdp))
            .clickable {
                onclick.invoke()
            }
            .background(color = color)
            .padding(8.sdp)


    ) {
        Text(
            text = text,
            fontSize = 12.ssp,
            color = textColor
        )
    }
}

@Composable
fun ColumnScope.ArticleList(
    navController: NavController,
    articleList: List<ArticleEntity>,
    articlesViewModel: ArticlesViewModel,
) {
    LazyColumn(
        modifier = Modifier
            .weight(1F)
            .padding(8.sdp)
    ) {
        items(articleList) {
            ArticleListItem(
                navController,
                it,
                articlesViewModel
            )
        }
    }
}

@Composable
fun ArticleListItem(
    navController: NavController,
    article: ArticleEntity,
    articlesViewModel: ArticlesViewModel,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.sdp)
            .clickable {
                navController.navigate(
                    route = Routes.DetailScreen.route, args = bundleOf(
                        "article" to article
                    )
                )
            }
    ) {
        Spacer(modifier = Modifier.width(8.sdp))
        if (!article.urlToImage.isNullOrEmpty()) {
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
        Spacer(modifier = Modifier.width(8.sdp))
        Text(
            text = article.title ?: "-",
            fontSize = 11.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

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

    Divider()

}

