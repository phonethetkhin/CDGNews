@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.cdg.cdg_news.ui.ui_resource.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.cdg.cdg_news.ui.ui_resource.theme.Red
import com.cdg.cdg_news.ui.ui_states.ArticleUIStates
import com.cdg.cdg_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    uiStates: ArticleUIStates,
    viewModel: NewsFeedViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit,

    ) {
    LaunchedEffect(Unit) {
        val categoryId = viewModel.getPreferredCategory() ?: 0

        val countryId = viewModel.getPreferredCountry() ?: 0
        val country =
            uiStates.availableCountries.find { it.id == countryId }?.name ?: "United States"

        val sources = viewModel.getPreferredSources()

        viewModel.toggleSelectedCategory(categoryId)
        viewModel.toggleSelectedCountry(country)

        if (sources!!.isNotEmpty()) {
            val sourcesList = sources.split(",")
            sourcesList.forEach {
                viewModel.toggleInitialSelectedSources(it)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.sdp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val scope = rememberCoroutineScope()

            SwitchWithLabel(
                label = "Filter By Sources?",
                isFilterBySource = uiStates.isFilterBySource
            ) {
                scope.launch {
                    viewModel.toggleSelectedFilterBySource(it)
                }
            }

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
            )

        }

        Divider(Modifier.padding(top = 8.sdp))

        if (uiStates.isFilterBySource) {
            FilterBySourceLayout(
                uiStates,
                onValueChangeToggle = { viewModel.toggleSource(it) },
                onSourceSelectedToggle = { viewModel.toggleSelectedSources(it) },
                onSave
            )
        } else {
            FilterByCategoryLayout(
                uiStates, viewModel, onSave
            )
        }

    }
}


@Composable
fun FilterByCategoryLayout(
    uiStates: ArticleUIStates,
    viewModel: NewsFeedViewModel,
    onSave: () -> Unit


) {
    Text(
        text = "Filter by category",
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.sdp)
    )
    Spacer(modifier = Modifier.height(4.sdp))
    CategorySelectionRow(
        uiStates = uiStates,
        toggleSelectedCategory = { viewModel.toggleSelectedCategory(it) })
    Spacer(modifier = Modifier.height(16.sdp))

    Divider()

    Text(
        text = "Filter by country",
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
        onItemSelected = viewModel::toggleSelectedCountry,
        modifier = Modifier.fillMaxWidth()
    )
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

