package com.cdg.cdg_news.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cdg.cdg_news.MainActivity
import com.cdg.cdg_news.R
import com.cdg.cdg_news.ui.ui_resource.composables.CommentUserInput
import com.cdg.cdg_news.ui.ui_resource.composables.MyButton
import com.cdg.cdg_news.ui.ui_resource.navigation.Routes
import com.cdg.cdg_news.ui.ui_states.ProfileUIStates
import com.cdg.cdg_news.util.datastore.MyDataStore
import com.cdg.cdg_news.util.getComponentActivity
import com.cdg.cdg_news.util.showToast
import com.cdg.cdg_news.viewmodel.ProfileViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch


//UIs
@Composable
fun LandingScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),

    ) {
    val context = LocalContext.current
    val activity = context.getComponentActivity() as MainActivity
    val dataStore: MyDataStore by lazy { activity.dataStore }

    val profileUIStates by profileViewModel.profileUIStates.collectAsState()

    LandingScreenContent(profileUIStates, profileViewModel, navController, dataStore)


}

@Composable
fun LandingScreenContent(
    uiStates: ProfileUIStates,
    viewModel: ProfileViewModel,
    navController: NavController,
    dataStore: MyDataStore
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1F)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ask_name),
                contentDescription = "AskName",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.sdp)
                    .height(150.sdp)
            )
            Text(
                text = "Welcome, Please tell us your name",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.sdp)
            )
            CommentUserInput(
                uiStates.userName,
                "Full Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.sdp),
                onValueChange = viewModel::toggleName
            )
        }

        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        MyButton(
            text = "Continue",
            textColor = Color.White,
            isLandingScreen = true,
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.sdp),
            buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            if (uiStates.userName.isNotEmpty()) {
                scope.launch {
                    dataStore.saveUserName(uiStates.userName)
                    dataStore.saveIsFirstLaunch(false)

                    navController.navigate(Routes.NewsFeedScreen.route) {
                        navController.currentDestination?.let {
                            popUpTo(it.id) {
                                inclusive = true
                            }
                        }
                    }

                }
            } else {
                context.showToast("Please enter your name")
            }
        }
    }
}

