package com.ptk.ptk_news.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.ptk_news.R
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.ui.ui_resource.composables.CommentBoxDialog
import com.ptk.ptk_news.ui.ui_resource.composables.DrawerContent
import com.ptk.ptk_news.ui.ui_resource.composables.NoConnectionDialog
import com.ptk.ptk_news.ui.ui_resource.composables.SearchView
import com.ptk.ptk_news.ui.ui_resource.navigation.Routes
import com.ptk.ptk_news.ui.ui_states.NewsFeedUIStates
import com.ptk.ptk_news.util.navigate
import com.ptk.ptk_news.viewmodel.ArticlesViewModel
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
    articlesViewModel: ArticlesViewModel = hiltViewModel(),

    ) {
    val uiStates by newsFeedViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = Unit) {
        newsFeedViewModel.getAllSources()

        newsFeedViewModel.getNewsFeed(1)
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
                        articlesViewModel,
                        uiStates,
                        newsFeedViewModel,
                        navController
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
    CommentBoxDialog(
        showDialog = uiStates.showCommentDialog,
        newsFeedViewModel,
        uiStates,
        onDismissRequest = { newsFeedViewModel.toggleCommentBoxDialog(false, 0) }) {

    }

    NoConnectionDialog(
        showDialog = uiStates.isShowDisconnectedDialog,
        onDismissRequest = { newsFeedViewModel.toggleIsShowDCDialog(false) })

}

@Composable
fun NewsFeedScreenContent(
    drawerState: DrawerState,
    articleList: List<ArticleEntity>,
    articlesViewModel: ArticlesViewModel,
    uiStates: NewsFeedUIStates,
    viewModel: NewsFeedViewModel,
    navController: NavController,
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
                hideKeyboard = drawerState.isOpen,
                onSearchValueChanged = viewModel::toggleSearchValueChange,
                modifier = Modifier.weight(1F)
            ) {
                scope.launch {
                    viewModel.getNewsFeed()
                }
            }
            Spacer(modifier = Modifier.width(4.sdp))

            Icon(
                imageVector = if (uiStates.selectedCategory == 0
                    && uiStates.selectedCountry == "All Countries"
                    && uiStates.availableSources.none { it.selected }
                ) Icons.Filled.FilterAltOff else Icons.Filled.FilterAlt,
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


        HeadlinesList(
            articleList = articleList,
            navController = navController,
            articlesViewModel,
            newsFeedViewModel = viewModel,
            uiStates
        )
    }

}

@Composable
fun ColumnScope.HeadlinesList(
    articleList: List<ArticleEntity>,
    navController: NavController,
    articlesViewModel: ArticlesViewModel,
    newsFeedViewModel: NewsFeedViewModel,
    uiStates: NewsFeedUIStates,
) {
    LazyColumn(
        modifier = Modifier
            .weight(1F)
            .padding(horizontal = 8.sdp)
    ) {
        items(articleList) {
            HeadlinesListItem(it, navController, articlesViewModel, newsFeedViewModel, uiStates)

        }
    }
}

@Composable
fun HeadlinesListItem(
    article: ArticleEntity,
    navController: NavController,
    articlesViewModel: ArticlesViewModel,
    viewModel: NewsFeedViewModel,
    uiStates: NewsFeedUIStates,
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
    ReactionBar(viewModel = viewModel, articlesViewModel, article, uiStates)
    Spacer(modifier = Modifier.height(16.sdp))
    Divider()
}

@Composable
fun ReactionBar(
    viewModel: NewsFeedViewModel,
    articlesViewModel: ArticlesViewModel,
    articleEntity: ArticleEntity,
    uiStates: NewsFeedUIStates
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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
            imageVector = Icons.Filled.Comment,
            contentDescription = "CommentIcon",
            modifier = Modifier
                .padding(16.sdp)
                .size(15.sdp)
                .clickable {
                    viewModel.toggleCommentBoxDialog(true, articleEntity.id)
                }
                .drawBehind {
                    drawCircle(
                        color = Color.Black,
                        radius = this.size.minDimension,
                        alpha = 0.25F
                    )
                },
            tint = MaterialTheme.colorScheme.onPrimary
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

        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = "ShareIcon",
            modifier = Modifier
                .padding(16.sdp)
                .size(15.sdp)
                .clickable {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${articleEntity.url}")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
                .drawBehind {
                    drawCircle(
                        color = Color.Black,
                        radius = this.size.minDimension,
                        alpha = 0.25F
                    )
                },
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

