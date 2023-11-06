@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.ptk.ptk_news.ui.ui_resource.theme.Red
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun FilterSourceDialog(
    showDialog: Boolean,
    uiStates: ArticleUIStates,
    viewModel: NewsFeedViewModel,
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

                    FilterBySourceLayout(uiStates = uiStates, viewModel = viewModel, onSave)
                }
            }
        }
    }
}

@Composable
fun ColumnScope.FilterBySourceLayout(
    uiStates: ArticleUIStates,
    viewModel: NewsFeedViewModel,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.weight(1F)) {
        Text(
            text = "Filter by source",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.sdp)
        )
        Spacer(modifier = Modifier.height(4.sdp))
        SourceSelectionRow(uiStates = uiStates, viewModel = viewModel)
        Spacer(modifier = Modifier.height(8.sdp))
        OutlinedTextField(
            onValueChange = viewModel::toggleSource,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp, start = 16.sdp, end = 16.sdp)
                .background(Color.White)
                .border(1.sdp, color = MaterialTheme.colorScheme.primary),
            value = uiStates.source,
        )
        SourceSuggestionList(uiStates = uiStates, viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.sdp))
    }

    MyButton(
        text = "Save", textColor = Color.White, buttonColor = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary
        ),

        modifier = Modifier.fillMaxWidth()
    ) {

        onSave.invoke()
    }
}