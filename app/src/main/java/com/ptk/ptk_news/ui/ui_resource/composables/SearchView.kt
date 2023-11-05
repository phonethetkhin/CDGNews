package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    selectedText: String,
    placeholder: String = "Search News",
    hideKeyboard: Boolean = false,
    onSearchValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = selectedText,
        modifier = modifier,
        onValueChange = { onSearchValueChanged.invoke(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                "Search Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(16.sdp)
                    .clickable { onSearch.invoke() }
            )

        },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                "Remove Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(16.sdp)
                    .clickable { onSearchValueChanged.invoke("") }
            )

        },
        keyboardActions = KeyboardActions(onDone = { onSearch.invoke() }),
        textStyle = TextStyle.Default.copy(
            color = Color.Black, // Text color
            textAlign = TextAlign.Start, // Text alignment
            fontSize = MaterialTheme.typography.labelSmall.fontSize, // Text size

        ),

        singleLine = true,
        shape = RoundedCornerShape(8.sdp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
        ),
    )
    if (hideKeyboard) {
        focusManager.clearFocus()
    }

}