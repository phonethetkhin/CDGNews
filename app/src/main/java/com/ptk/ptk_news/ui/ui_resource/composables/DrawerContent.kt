@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.ptk.ptk_news.ui.ui_resource.theme.Red
import com.ptk.ptk_news.ui.ui_states.NewsFeedUIStates
import com.ptk.ptk_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DrawerContent(
    uiStates: NewsFeedUIStates,
    viewModel: NewsFeedViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .padding(8.sdp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SwitchWithLabel(
                label = "Filter By Sources?",
                isFilterBySource = uiStates.isFilterBySource
            ) {
                viewModel.toggleSelectedFilterBySource(it)
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
                            radius = 40F
                        )
                    }
            )


        }
        Divider()

        if (uiStates.isFilterBySource) {
            FilterBySourceLayout(uiStates, viewModel, onSave)
        } else {
            FilterByCategoryLayout(
                uiStates, viewModel, onSave
            )
        }

    }
}

@Composable
fun FilterBySourceLayout(
    uiStates: NewsFeedUIStates,
    viewModel: NewsFeedViewModel,
    onSave: () -> Unit
) {
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


    MyButton(
        text = "Save", textColor = Color.White, buttonColor = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary
        ),

        modifier = Modifier.fillMaxWidth()
    ) {
        onSave.invoke()
    }
}

@Composable
fun FilterByCategoryLayout(
    uiStates: NewsFeedUIStates,
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

            Row(modifier = Modifier
                .padding(top = 8.sdp)
                .background(color = color, shape = CircleShape)
                .border(
                    width = border,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clickable {
                    viewModel.toggleSelectedCategory(category.id)
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
                    modifier = Modifier
                        .alpha(alpha),
                    tint = Color.White
                )

            }
        }
    }
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