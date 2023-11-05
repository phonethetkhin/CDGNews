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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ptk.ptk_news.MainActivity
import com.ptk.ptk_news.ui.ui_resource.navigation.Routes
import com.ptk.ptk_news.util.datastore.MyDataStore
import com.ptk.ptk_news.util.getComponentActivity
import com.ptk.ptk_news.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first


//UIs
@Composable
fun SplashScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.getComponentActivity() as MainActivity
    val dataStore: MyDataStore by lazy { activity.dataStore }

    LaunchedEffect(Unit) {
        delay(2000L)
        if (dataStore.isFirstLaunch.first()!!) {
            homeViewModel.getSources()
            navController.navigate(Routes.LandingScreen.route) {
                navController.currentDestination?.let {
                    popUpTo(it.id) {
                        inclusive = true
                    }
                }
            }
            dataStore.saveIsFirstLaunch(false)
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
    SplashScreenContent()


}

@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

    }

}

