@file:OptIn(ExperimentalLayoutApi::class)

package com.ptk.ptk_news.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ptk.ptk_news.MainActivity
import com.ptk.ptk_news.ui.ui_resource.composables.MyButton
import com.ptk.ptk_news.ui.ui_resource.theme.Blue
import com.ptk.ptk_news.ui.ui_resource.theme.Orange
import com.ptk.ptk_news.ui.ui_resource.theme.Purple
import com.ptk.ptk_news.ui.ui_resource.theme.Yellow
import com.ptk.ptk_news.ui.ui_states.ProfileUIStates
import com.ptk.ptk_news.util.getComponentActivity
import com.ptk.ptk_news.viewmodel.ProfileViewModel
import ir.kaaveh.sdpcompose.sdp


//UIs
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),

    ) {

    LaunchedEffect(Unit) {
        val themeId = profileViewModel.getThemeId() ?: 1
        profileViewModel.toggleThemeId(themeId)
    }
    val context = LocalContext.current
    val activity = context.getComponentActivity() as MainActivity

    val uiStates by profileViewModel.uiStates.collectAsState()

    ProfileScreenContent(uiStates, profileViewModel, activity)


}

@Composable
fun ProfileScreenContent(
    uiStates: ProfileUIStates,
    viewModel: ProfileViewModel,
    activity: MainActivity
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.weight(1F)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.sdp, top = 8.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val color = MaterialTheme.colorScheme.primary
                Text(
                    modifier = Modifier
                        .padding(16.sdp)
                        .drawBehind {
                            drawCircle(
                                color = color,
                                radius = this.size.maxDimension
                            )
                        },
                    text = "P",
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Spacer(modifier = Modifier.width(8.sdp))
                Text(
                    text = "Phone Thet Khine",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.Black
                )
            }
            Divider(modifier = Modifier.fillMaxWidth())
            Text(
                "Theme Color",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.sdp)
            )

            ColorSelectionRow(uiStates = uiStates, viewModel, activity = activity)

            Divider()
            Text(
                "Font Size",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.sdp)
            )
            TextSizeSelectionRow(uiStates = uiStates, viewModel = viewModel)
            Divider()

            TextButton(onClick = {}, modifier = Modifier.align(Alignment.End)) {
                Text(
                    text = "Edit Preferred Filters",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
        MyButton(
            text = "Save",
            textColor = MaterialTheme.colorScheme.onPrimary,
            buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.sdp)
        ) {

        }

    }
}

@Composable
fun ColorSelectionRow(
    uiStates: ProfileUIStates,
    viewModel: ProfileViewModel,
    activity: MainActivity
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.sdp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.sdp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Orange)
                .size(50.sdp)
                .clickable {
                    viewModel.toggleThemeId(1)
                    activity.recreate()
                }
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "CheckIcon",
                tint = Color.White,
                modifier = Modifier
                    .size(25.sdp)
                    .alpha(alpha = if (uiStates.themeId == 1) 1F else 0F)

            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Yellow)
                .size(50.sdp)
                .clickable {
                    viewModel.toggleThemeId(2)
                    activity.recreate()

                }
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "CheckIcon",
                tint = Color.Black,
                modifier = Modifier
                    .size(25.sdp)
                    .alpha(alpha = if (uiStates.themeId == 2) 1F else 0F)

            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Blue)
                .size(50.sdp)
                .clickable {
                    viewModel.toggleThemeId(3)
                    activity.recreate()

                }
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "CheckIcon",
                tint = Color.White,
                modifier = Modifier
                    .size(25.sdp)
                    .alpha(alpha = if (uiStates.themeId == 3) 1F else 0F)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Purple)
                .size(50.sdp)
                .clickable {
                    viewModel.toggleThemeId(4)
                    activity.recreate()

                }
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "CheckIcon",
                tint = Color.White,
                modifier = Modifier
                    .size(25.sdp)
                    .alpha(alpha = if (uiStates.themeId == 4) 1F else 0F)

            )
        }
    }
}

@Composable
fun TextSizeSelectionRow(uiStates: ProfileUIStates, viewModel: ProfileViewModel) {
    FlowRow(

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.sdp),
        horizontalArrangement = Arrangement.spacedBy(7.sdp),
    ) {
        uiStates.availableTextSize.forEach { textSizeString ->
            val color =
                if (textSizeString == uiStates.selectedTextSize) MaterialTheme.colorScheme.primary else Color.Transparent
            val textColor =
                if (textSizeString == uiStates.selectedTextSize) Color.White else Color.Black
            val border = if (textSizeString == uiStates.selectedTextSize) 0.sdp else 1.sdp
            val alpha = if (textSizeString == uiStates.selectedTextSize) 1F else 0F

            Row(modifier = Modifier
                .padding(top = 8.sdp)
                .background(color = color, shape = CircleShape)
                .border(
                    width = border,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clickable {
                    viewModel.toggleSelectedTextSizeString(textSizeString)
                }
                .padding(8.sdp)
            ) {
                Text(
                    text = textSizeString,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
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
}

