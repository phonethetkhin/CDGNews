package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SourceSuggestionList(uiStates: ArticleUIStates, viewModel: NewsFeedViewModel) {
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
}