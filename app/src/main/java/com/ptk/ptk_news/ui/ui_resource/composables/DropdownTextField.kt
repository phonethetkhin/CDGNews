package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    selectedText: String,
    items: List<String>,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var dropDownWidth by remember { mutableStateOf(0) }

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown

    OutlinedTextField(
        value = selectedText,
        onValueChange = { },
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
                imageVector = icon,
                "Expand Icon",
                modifier = Modifier
                    .size(16.sdp)
                    .clickable { expanded = !expanded }
            )

        },
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
        readOnly = true,
        modifier = modifier
            .onSizeChanged { dropDownWidth = it.width }
            .clickable {
                expanded = !expanded
            },
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current) { dropDownWidth.toDp() })
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = item,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                },
                onClick = {
                    onItemSelected.invoke(item)
                    expanded = !expanded
                })
        }
    }
}