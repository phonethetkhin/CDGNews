package com.ptk.ptk_news.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun String?.isEmailFormat() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String?.nonNullString(): String {
    return this ?: "-"
}

fun String.stringToByteArray(): ByteArray {
    return Base64.decode(this, Base64.DEFAULT)
}

fun ByteArray.byteArrayToBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Context.getComponentActivity(): ComponentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

fun String.getConvertDate(sourceFormat: String, targetedFormat: String): String {
    val originalFormat: DateFormat = SimpleDateFormat(sourceFormat, Locale.ENGLISH)
    val targetFormat: DateFormat = SimpleDateFormat(targetedFormat, Locale.ENGLISH)

    return try {
        val date = originalFormat.parse(this)
        targetFormat.format(date)
    } catch (e: Throwable) {
        ""
    }
}


fun String?.notNullString(): String {
    return if (this.isNullOrEmpty()) {
        "-"
    } else {
        this
    }
}




