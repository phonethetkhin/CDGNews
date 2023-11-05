@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.ptk_news.R
import com.ptk.ptk_news.model.dto.response.ArticlesItem
import com.ptk.ptk_news.ui.ui_resource.composables.FilterSourceDialog
import com.ptk.ptk_news.ui.ui_resource.composables.SearchView
import com.ptk.ptk_news.ui.ui_states.ArticlesUIStates
import com.ptk.ptk_news.viewmodel.ArticlesViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch


//UIs
@Composable
fun ArticlesScreen(
    navController: NavController,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),

    ) {
    val uiStates by articlesViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {

        articlesViewModel.getAllSources()

    }
    ArticlesScreenContent(uiStates, articlesViewModel)

    val scope = rememberCoroutineScope()
    FilterSourceDialog(
        showDialog = uiStates.isShowFilterDialog,
        uiStates,
        articlesViewModel,
        onDismissRequest = {
            articlesViewModel.resetSelectedValue()
            articlesViewModel.toggleIsShowFilterSourceDialog(false)
        }) {
        scope.launch {
            articlesViewModel.savePreferredSetting()
            articlesViewModel.getArticles()
            articlesViewModel.toggleIsShowFilterSourceDialog(false)
        }
    }

}

@Composable
fun ArticlesScreenContent(
    uiStates: ArticlesUIStates,
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
                selectedText = uiStates.searchText,
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.sdp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(8.sdp))
                    .clickable {
                        viewModel.toggleSortBy(1)
                        scope.launch {
                            viewModel.getArticles()
                        }
                    }
                    .background(color = if (uiStates.selectedSortBy == 1) MaterialTheme.colorScheme.secondary else Color.Transparent)
                    .padding(8.sdp)

            ) {
                Text(
                    text = "Relevancy",
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = if (uiStates.selectedSortBy == 1) MaterialTheme.colorScheme.onSecondary else Color.Black
                )
            }
            Box(
                Modifier
                    .clip(RoundedCornerShape(8.sdp))
                    .clickable {
                        viewModel.toggleSortBy(2)
                        scope.launch {
                            viewModel.getArticles()
                        }

                    }
                    .background(color = if (uiStates.selectedSortBy == 2) MaterialTheme.colorScheme.secondary else Color.Transparent)
                    .padding(8.sdp)

            ) {
                Text(
                    text = "Popularity",
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = if (uiStates.selectedSortBy == 2) MaterialTheme.colorScheme.onSecondary else Color.Black
                )
            }
            Box(
                Modifier
                    .clip(RoundedCornerShape(8.sdp))
                    .clickable {
                        viewModel.toggleSortBy(3)
                        scope.launch {
                            viewModel.getArticles()
                        }
                    }
                    .background(color = if (uiStates.selectedSortBy == 3) MaterialTheme.colorScheme.secondary else Color.Transparent)
                    .padding(8.sdp)


            ) {
                Text(
                    text = "PublishedAt",
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = if (uiStates.selectedSortBy == 3) MaterialTheme.colorScheme.onSecondary else Color.Black
                )
            }
        }

        ArticleList(uiStates.articlesList)
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

