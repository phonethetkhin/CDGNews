package com.cdg.cdg_news.ui.ui_resource.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.cdg.cdg_news.ui.ui_states.ArticleUIStates
import ir.kaaveh.sdpcompose.sdp

@Composable
fun FilterBySourceLayout(
    uiStates: ArticleUIStates,
    onValueChangeToggle: (String) -> Unit,
    onSourceSelectedToggle: (String) -> Unit,
    onSave: () -> Unit
) {
    Log.e("ajsdfklajsdkf2", uiStates.source)
    Text(
        text = "Filter by source",
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.sdp)
    )
    Spacer(modifier = Modifier.height(4.sdp))
    SourceSelectionRow(uiStates = uiStates) {
        onSourceSelectedToggle.invoke(it)

    }
    Spacer(modifier = Modifier.height(8.sdp))
    OutlinedTextField(
        onValueChange = { onValueChangeToggle.invoke(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.sdp, start = 16.sdp, end = 16.sdp)
            .background(Color.White)
            .border(1.sdp, color = MaterialTheme.colorScheme.primary),
        value = uiStates.source,
    )
    SourceSuggestionList(uiStates = uiStates) { onSourceSelectedToggle.invoke(it) }

    Spacer(modifier = Modifier.height(16.sdp))


    MyButton(
        text = "Save", textColor = Color.White, buttonColor = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary
        ),

        modifier = Modifier.fillMaxWidth()
    ) {

        onSave.invoke()
    }
}