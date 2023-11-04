package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import ir.kaaveh.sdpcompose.sdp


@Composable
fun MyButton(
    text: String,
    textColor: Color,
    enable: Boolean = true,
    buttonColor: ButtonColors,
    modifier: Modifier = Modifier,
    buttonClick: () -> Unit,
) {
    Button(
        onClick = buttonClick,
        modifier = modifier,
        enabled = enable,
        shape = RoundedCornerShape(32.sdp),
        colors = buttonColor

    ) {
        Text(
            text,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(top = 4.sdp, bottom = 4.sdp)
        )
    }
}