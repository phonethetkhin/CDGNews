package com.ptk.ptk_news.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ptk.ptk_news.MainActivity
import com.ptk.ptk_news.ui.ui_resource.navigation.Routes
import com.ptk.ptk_news.util.datastore.MyDataStore
import com.ptk.ptk_news.util.getComponentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first


//UIs
@Composable
fun LandingScreen(
    navController: NavController,

    ) {

    LandingScreenContent()


}

@Composable
fun LandingScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

    }

}

