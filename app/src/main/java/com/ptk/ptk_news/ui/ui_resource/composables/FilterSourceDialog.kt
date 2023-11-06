@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import com.ptk.ptk_news.ui.ui_resource.theme.Red
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import com.ptk.ptk_news.viewmodel.ArticlesViewModel
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun FilterSourceDialog(
    showDialog: Boolean,
    uiStates: ArticleUIStates,
    viewModel: NewsFeedViewModel,
    articlesViewModel: ArticlesViewModel,
    onDismissRequest: () -> Unit,
    onSave: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest.invoke() },
        ) {

            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.sdp)
                ) {

                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 4.sdp)
                            .clickable { onDismissRequest.invoke() }
                            .drawBehind {
                                drawCircle(
                                    color = Red,
                                    radius = this.size.maxDimension
                                )
                            }
                    )

                    FilterBySourceLayout(uiStates = uiStates, onValueChangeToggle = {articlesViewModel.toggleSource(it)}, onSourceSelectedToggle = {
                        articlesViewModel.toggleSelectedSources(it)

                    }, onSave)
                }
            }
        }
    }
}

