@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ptk.ptk_news.ui.ui_resource.theme.Red
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CommentBoxDialog(
    showDialog: Boolean,
    newsFeedViewModel: NewsFeedViewModel,
    uiStates: ArticleUIStates,
    onDismissRequest: () -> Unit,
) {
    if (showDialog) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            ),
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
                        .padding(8.sdp)
                ) {
                    Spacer(modifier = Modifier.height(8.sdp))
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 4.sdp)
                            .clickable { onDismissRequest.invoke() }
                            .drawBehind {
                                drawCircle(
                                    color = Red,
                                    radius = this.size.maxDimension
                                )
                            }
                            .align(Alignment.End)
                    )
                    Spacer(modifier = Modifier.height(16.sdp))
                    CommentList(uiStates = uiStates)
                    BottomBar(newsFeedViewModel, uiStates)

                    Spacer(modifier = Modifier.imePadding())

                }
            }
        }
    }
}

@Composable
fun ColumnScope.CommentList(uiStates: ArticleUIStates) {
    if (uiStates.commentList.isEmpty()) {
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(8.sdp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "There are no comments yet.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 8.sdp)
        ) {
            items(uiStates.commentList) {
                CommentListItem(it)

            }
        }
    }
}

@Composable
fun CommentListItem(commentItem: String) {
    Spacer(modifier = Modifier.height(16.sdp))
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(verticalAlignment = Alignment.Top) {
            val color = MaterialTheme.colorScheme.primary
            Text(
                modifier = Modifier
                    .padding(16.sdp)
                    .drawBehind {
                        drawCircle(
                            color = color,
                            radius = this.size.maxDimension
                        )
                    },
                text = "P",
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Spacer(modifier = Modifier.width(8.sdp))

            Column(
                modifier = Modifier
                    .weight(1F)
                    .background(Color.LightGray, shape = RoundedCornerShape(16.sdp))
                    .padding(
                        8.sdp
                    )
            ) {
                Text(
                    text = "Phone Thet Khine",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = commentItem,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = Color.Black,
                )

                Text(
                    text = "11:PM",
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    modifier = Modifier.align(Alignment.End),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
fun ColumnScope.BottomBar(newsFeedViewModel: NewsFeedViewModel, uiStates: ArticleUIStates) {

    CommentUserInput(
        uiStates.commentText,
        "Please enter public comment",
        modifier = Modifier
            .fillMaxWidth(),
        onValueChange = newsFeedViewModel::toggleCommentText
    )
    Spacer(modifier = Modifier.height(8.sdp))
    Icon(
        imageVector = Icons.Filled.Send,
        contentDescription = "CommentIcon",
        tint = Color.Black,
        modifier = Modifier
            .align(Alignment.End)
            .clickable {
                newsFeedViewModel.postComment()
            }
            .padding(8.sdp)
    )
}