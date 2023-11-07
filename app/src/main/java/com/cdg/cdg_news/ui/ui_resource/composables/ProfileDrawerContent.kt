@file:OptIn(ExperimentalLayoutApi::class)

package com.cdg.cdg_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import com.cdg.cdg_news.ui.ui_resource.theme.Red
import com.cdg.cdg_news.ui.ui_states.ArticleUIStates
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ProfileDrawerContent(
    uiStates: ArticleUIStates,
    toggleSelectedCategory: (Int) -> Unit,
    toggleSelectedCountry: (String) -> Unit,
    onValueChangeToggle: (String) -> Unit,
    onSourceSelectedToggle: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.sdp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(16.sdp))
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close Icon",
            tint = Color.White,
            modifier = Modifier
                .padding(end = 4.sdp)
                .clickable { onDismiss.invoke() }
                .drawBehind {
                    drawCircle(
                        color = Red,
                        radius = this.size.minDimension
                    )
                }
                .size(15.sdp)
                .align(Alignment.End)
        )


        PFilterByCategoryLayout(
            uiStates, toggleSelectedCategory, toggleSelectedCountry
        )
        Spacer(modifier = Modifier.height(16.sdp))

        Divider()
        PFilterBySourceLayout(
            uiStates,
            onValueChangeToggle =
            onValueChangeToggle::invoke,
            onSourceSelectedToggle = onSourceSelectedToggle::invoke
        )
        MyButton(
            text = "Save", textColor = Color.White, buttonColor = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.primary
            ),

            modifier = Modifier.fillMaxWidth()
        ) {

            onSave.invoke()

        }
    }
}


@Composable
fun PFilterByCategoryLayout(
    uiStates: ArticleUIStates,
    toggleSelectedCategory: (Int) -> Unit,
    toggleSelectedCountry: (String) -> Unit,
) {
    Text(
        text = "Preferred Category",
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.sdp)
    )
    Spacer(modifier = Modifier.height(4.sdp))
    CategorySelectionRow(uiStates) { toggleSelectedCategory.invoke(it) }
    Spacer(modifier = Modifier.height(16.sdp))

    Divider()

    Text(
        text = "Preferred Country",
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.sdp)
    )
    Spacer(modifier = Modifier.height(4.sdp))

    DropdownTextField(
        placeholder = "Please Select Country",
        selectedText = uiStates.selectedCountry,
        items = uiStates.availableCountries.map { it.name },
        onItemSelected = toggleSelectedCountry::invoke,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.sdp))


}


@Composable
fun PFilterBySourceLayout(
    uiStates: ArticleUIStates,
    onValueChangeToggle: (String) -> Unit,
    onSourceSelectedToggle: (String) -> Unit,
) {
    Text(
        text = "Preferred Source",
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.sdp)
    )
    Spacer(modifier = Modifier.height(4.sdp))

    SourceSelectionRow(uiStates = uiStates) { onSourceSelectedToggle.invoke(it) }

    Spacer(modifier = Modifier.height(8.sdp))

    OutlinedTextField(
        onValueChange = onValueChangeToggle::invoke,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.sdp, start = 16.sdp, end = 16.sdp)
            .background(Color.White)
            .border(1.sdp, color = MaterialTheme.colorScheme.primary),
        value = uiStates.source,
    )
    SourceSuggestionList(uiStates = uiStates) { onSourceSelectedToggle.invoke(it) }

    Spacer(modifier = Modifier.height(16.sdp))


}


