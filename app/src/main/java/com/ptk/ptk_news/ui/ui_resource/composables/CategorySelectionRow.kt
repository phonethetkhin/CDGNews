@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CategorySelectionRow(uiStates: ArticleUIStates, toggleSelectedCategory: (Int) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(7.sdp),
    ) {
        uiStates.availableCategories.forEach { category ->
            val color =
                if (category.id == uiStates.selectedCategory) MaterialTheme.colorScheme.primary else Color.Transparent
            val textColor =
                if (category.id == uiStates.selectedCategory) Color.White else Color.Black
            val border = if (category.id == uiStates.selectedCategory) 0.sdp else 1.sdp
            val alpha = if (category.id == uiStates.selectedCategory) 1F else 0F

            Row(verticalAlignment = CenterVertically, modifier = Modifier
                .padding(top = 8.sdp)
                .background(color = color, shape = CircleShape)
                .border(
                    width = border,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clickable {
                    toggleSelectedCategory.invoke(category.id)
                }
                .padding(8.sdp)
            ) {
                Text(
                    text = category.name,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = textColor,
                    modifier = Modifier
                        .padding(horizontal = 8.sdp)

                )
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "CheckIcon",
                    modifier = Modifier.size(25.sdp)
                        .alpha(alpha),
                    tint = Color.White
                )

            }
        }
    }
}