package com.cdg.cdg_news.ui.screen

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cdg.cdg_news.MainActivity
import com.cdg.cdg_news.R
import com.cdg.cdg_news.ui.ui_resource.composables.NoConnectionDialog
import com.cdg.cdg_news.ui.ui_resource.navigation.Routes
import com.cdg.cdg_news.util.datastore.MyDataStore
import com.cdg.cdg_news.util.getComponentActivity
import com.cdg.cdg_news.viewmodel.HomeViewModel
import com.cdg.cdg_news.viewmodel.NewsFeedViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


//UIs
@Composable
fun SplashScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    newsFeedViewModel: NewsFeedViewModel = hiltViewModel(),

    ) {
    val uiStates by newsFeedViewModel.uiStates.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context.getComponentActivity() as MainActivity
    val dataStore: MyDataStore by lazy { activity.dataStore }

    LaunchedEffect(Unit) {
        checkConnRoute(context, dataStore, homeViewModel, newsFeedViewModel, navController)
    }
    SplashScreenContent()


    NoConnectionDialog(
        showDialog = uiStates.isShowDisconnectedDialog,
        isRetry = true,
        dismissOnBackPress = false,
        dismissOnTouchOutSide = false,
        onDismissRequest = { newsFeedViewModel.toggleIsShowDCDialog(false) }) {
        scope.launch {
            checkConnRoute(context, dataStore, homeViewModel, newsFeedViewModel, navController)
        }
    }
}

@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.cdg_logos_foreground),
            contentDescription = "CDGLogo", modifier = Modifier.size(150.sdp)
        )

        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)

    }

}

suspend fun checkConnRoute(
    context: Context,
    dataStore: MyDataStore,
    homeViewModel: HomeViewModel,
    newsFeedViewModel: NewsFeedViewModel,
    navController: NavController
) {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
    val connected = activeNetworkInfo != null && activeNetworkInfo.isConnected()

    delay(2000L)
    if (dataStore.isFirstLaunch.first()!!) {
        if (connected) {
            homeViewModel.getSources()
            navController.navigate(Routes.LandingScreen.route) {
                navController.currentDestination?.let {
                    popUpTo(it.id) {
                        inclusive = true
                    }
                }
            }
        } else {
            newsFeedViewModel.toggleIsShowDCDialog(true)
        }
    } else {
        navController.navigate(Routes.NewsFeedScreen.route) {
            navController.currentDestination?.let {
                popUpTo(it.id) {
                    inclusive = true
                }
            }
        }
    }
}

