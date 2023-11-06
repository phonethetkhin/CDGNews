@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.ptk.ptk_news.ui.ui_resource.theme.Red
import com.ptk.ptk_news.ui.ui_states.ArticlesUIStates
import com.ptk.ptk_news.viewmodel.ArticlesViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun FilterSourceDialog(
    showDialog: Boolean,
    uiStates: ArticlesUIStates,
    viewModel: ArticlesViewModel,
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
    uiStates: ArticlesUIStates,
    viewModel: ArticlesViewModel,
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
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(7.sdp),
        ) {
            uiStates.availableSources.filter { it.selected }.forEach { source ->

                Row(modifier = Modifier
                    .padding(top = 8.sdp)
                    .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clickable {
                        viewModel.toggleSelectedSources(source.name!!)
                    }
                    .padding(8.sdp)
                ) {
                    Text(
                        text = source.name!!,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 8.sdp)

                    )
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "DeleteIcon",
                        modifier = Modifier
                            .alpha(1F),
                        tint = Color.White

                    )

                }
            }
        }
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.sdp)
                .alpha(alpha = if (uiStates.sourceSuggestions.isNotEmpty()) 1F else 0F)
                .padding(start = 16.sdp, end = 16.sdp)
                .clip(RoundedCornerShape(bottomStart = 10.sdp, bottomEnd = 10.sdp))
                .background(Color.LightGray),
        ) {
            items(uiStates.sourceSuggestions) {
                Text(
                    it, modifier = Modifier
                        .padding(start = 8.sdp, top = 8.sdp)
                        .clickable { viewModel.toggleSelectedSources(it) }
                )
            }

        }
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