@file:OptIn(ExperimentalMaterial3Api::class)

package com.cdg.cdg_news.ui.ui_resource.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.cdg.cdg_news.R
import com.cdg.cdg_news.ui.ui_resource.theme.Red
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun NoConnectionDialog(
    showDialog: Boolean,
    isRetry: Boolean = false,
    dismissOnBackPress: Boolean = true,
    dismissOnTouchOutSide: Boolean = true,
    onDismissRequest: () -> Unit,
    onRetry: () -> Unit = {},
) {
    if (showDialog) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnTouchOutSide
            ),
            onDismissRequest = { onDismissRequest.invoke() }) {

            Card {
                Column(modifier = Modifier.padding(16.sdp)) {
                    if (!isRetry) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .clickable { onDismissRequest.invoke() }
                                .drawBehind {
                                    drawCircle(
                                        color = Red,
                                        radius = 30F
                                    )
                                }
                                .align(Alignment.End)
                        )
                        Spacer(modifier = Modifier.height(16.sdp))
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.sdp),
                        text = "No Internet",
                        fontSize = 16.ssp,
                        color = Red,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.sdp))

                    val composition by rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(resId = R.raw.disconnected)
                    )

                    // render the animation
                    LottieAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.sdp), composition = composition,
                        iterations = LottieConstants.IterateForever // animate forever

                    )

                    Spacer(modifier = Modifier.height(16.sdp))

                    Text(
                        text = "Please check your internet connection",
                        fontSize = 11.ssp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(alpha = if (isRetry) 1F else 0F)
                            .padding(8.sdp),
                        onClick = { onRetry.invoke() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Retry", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    }

                }

            }
        }
    }
}