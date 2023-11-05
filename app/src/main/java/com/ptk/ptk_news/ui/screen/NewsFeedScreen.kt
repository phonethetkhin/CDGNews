@file:JvmName("NewsFeedScreenKt")
@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.screen

import android.annotation.SuppressLint
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.ptk_news.R
import com.ptk.ptk_news.model.dto.response.ArticlesItem
import com.ptk.ptk_news.ui.ui_resource.composables.DrawerContent
import com.ptk.ptk_news.ui.ui_resource.composables.SearchView
import com.ptk.ptk_news.ui.ui_states.NewsFeedUIStates
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch


//UIs
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsFeedScreen(
    navController: NavController,
    newsFeedViewModel: NewsFeedViewModel = hiltViewModel(),

    ) {
    val uiStates by newsFeedViewModel.uiStates.collectAsState()

    LaunchedEffect(key1 = Unit) {
        /* newsFeedViewModel.getSources()
         newsFeedViewModel.getNewsFeed()*/
    }


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    if (drawerState.isClosed) {
        newsFeedViewModel.resetSelectedValue()
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ModalDrawerSheet(
                        modifier = Modifier
                            .then(
                                if (drawerState.targetValue == DrawerValue.Open) Modifier.fillMaxSize() else Modifier
                            ),
                    ) {
                        DrawerContent(
                            uiStates, newsFeedViewModel, onDismiss = {
                                if (drawerState.isOpen) {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                                newsFeedViewModel.resetSelectedValue()
                            }
                        ) {

                            coroutineScope.launch {
                                newsFeedViewModel.savePreferredSetting()
                                newsFeedViewModel.getNewsFeed()
                                if (drawerState.isOpen) {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                            }
                        }
                    }
                }
            },
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                Scaffold() {

                    NewsFeedScreenContent(
                        drawerState,
                        uiStates.newsFeedList,
                        uiStates,
                        newsFeedViewModel
                    )
                }

            }
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

}

@Composable
fun NewsFeedScreenContent(
    drawerState: DrawerState,
    articleList: List<ArticlesItem>,
    uiStates: NewsFeedUIStates,
    viewModel: NewsFeedViewModel,
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
                    viewModel.getNewsFeed()
                }
            }
            Spacer(modifier = Modifier.width(4.sdp))

            Icon(
                imageVector = Icons.Filled.FilterAlt,
                contentDescription = "FilterIcon",
                modifier = Modifier
                    .size(30.sdp)
                    .clickable {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            }
                        }
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }


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

