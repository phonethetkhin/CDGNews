@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SourceSelectionRow(uiStates: ArticleUIStates, onSelectedSource: (String) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(7.sdp),
    ) {

        uiStates.availableSources.filter { it.selected }.forEach { source ->

            Row(modifier = Modifier
                .padding(top = 8.sdp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                .clickable {
                    onSelectedSource.invoke(source.name!!)
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
}