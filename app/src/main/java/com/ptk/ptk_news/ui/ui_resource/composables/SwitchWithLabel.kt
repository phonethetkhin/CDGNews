package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import ir.kaaveh.sdpcompose.sdp

@Composable
fun RowScope.SwitchWithLabel(
    label: String,
    isFilterBySource: Boolean,
    onSwitchChange: (Boolean) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .weight(1F)
            .clickable(
                interactionSource = interactionSource,
                // This is for removing ripple when Row is clicked
                indication = null,
                role = Role.Switch,
                onClick = {
                    onSwitchChange.invoke(!isFilterBySource)
                }
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(text = label, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        Spacer(modifier = Modifier.padding(start = 8.sdp))
        Switch(
            checked = isFilterBySource,
            onCheckedChange = {
                onSwitchChange.invoke(it)
            }
        )
    }
}