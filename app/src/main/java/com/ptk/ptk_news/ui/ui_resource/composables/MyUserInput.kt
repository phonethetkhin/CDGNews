package com.ptk.ptk_news.ui.ui_resource.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.MyUserInput(
    value: String,
    cityNameEmpty: Boolean = false,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,

        modifier = Modifier
            .weight(1F),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.dp),

        textStyle = LocalTextStyle.current.copy(
            fontSize = 14.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        ),
        placeholder = {
            Text(
                "City name",
                fontSize = 14.ssp,
            )
        },
        supportingText = {
            if (cityNameEmpty) {
                Text(
                    text = "City Name must not be empty!!!",
                    fontSize = 10.ssp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }, trailingIcon = {
            if (cityNameEmpty) {
                Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.MyUserInputTrailing(
    value: String,
    dateEmpty: Boolean,
    dateClicked: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = {},
        modifier = Modifier
            .weight(1F),
        readOnly = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.dp),

        textStyle = LocalTextStyle.current.copy(
            fontSize = 12.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        ),
        trailingIcon = {
            IconButton(onClick = dateClicked) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "DateIcon",
                    modifier = Modifier.size(24.sdp)
                )
            }
        },
        keyboardOptions = KeyboardOptions(),
        placeholder = {
            Text(
                text = "Date",
                fontSize = 12.ssp,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        supportingText = {
            if (dateEmpty) {
                Text(
                    text = "Date must not be empty!!!",
                    fontSize = 10.ssp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.MyUserInput(
    placeHolder: String,
    value: String,
    valueEmpty: Boolean = false,
    userNameNotExist: Boolean? = false,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.dp),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 14.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        ),
        placeholder = {
            Text(
                placeHolder,
                fontSize = 14.ssp,
            )
        },
        supportingText = {
            if (valueEmpty) {
                Text(
                    text = "$placeHolder must not be empty!!!",
                    fontSize = 10.ssp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (userNameNotExist != null && userNameNotExist == false) {
                Text(
                    text = "This username already taken!!!",
                    fontSize = 10.ssp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        trailingIcon = {
            if (valueEmpty) {
                Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
            }
        },

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.MyUserInputTrailing(
    placeHolder: String,
    value: String,
    valueEmpty: Boolean = false,
    passwordLengthShort: Boolean = false,
    passCPassNotSame: Boolean = false,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var showPassword by remember { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,

        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(32.sdp),

        textStyle = LocalTextStyle.current.copy(
            fontSize = 14.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            val image = if (showPassword)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Localized description for accessibility services
            val description = if (showPassword) "Hide password" else "Show password"

            // Toggle button to hide or display password
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = image,
                    description,
                    modifier = Modifier
                        .size(32.sdp)
                )
            }
        },
        supportingText = {
            if (valueEmpty) {
                Text(
                    text = "$placeHolder must not be empty!!!",
                    fontSize = 10.ssp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (passwordLengthShort) {
                Text(
                    text = "Password must be at least 6 characters",
                    fontSize = 10.ssp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (passCPassNotSame) {
                Text(
                    text = "Password and Confirm Password not same",
                    fontSize = 10.ssp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 14.ssp,
            )
        },

        )
}