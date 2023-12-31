package com.cdg.cdg_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cdg.cdg_news.model.ui_model.bottomNavigationItems
import com.cdg.cdg_news.ui.ui_resource.navigation.NavGraph
import com.cdg.cdg_news.ui.ui_resource.navigation.Routes
import com.cdg.cdg_news.ui.ui_resource.theme.DarkColorSchemeBlue
import com.cdg.cdg_news.ui.ui_resource.theme.DarkColorSchemeOrange
import com.cdg.cdg_news.ui.ui_resource.theme.DarkColorSchemePurple
import com.cdg.cdg_news.ui.ui_resource.theme.DarkColorSchemeYellow
import com.cdg.cdg_news.ui.ui_resource.theme.LargeFontSize
import com.cdg.cdg_news.ui.ui_resource.theme.LightColorSchemeBlue
import com.cdg.cdg_news.ui.ui_resource.theme.LightColorSchemeOrange
import com.cdg.cdg_news.ui.ui_resource.theme.LightColorSchemePurple
import com.cdg.cdg_news.ui.ui_resource.theme.LightColorSchemeYellow
import com.cdg.cdg_news.ui.ui_resource.theme.MediumFontSize
import com.cdg.cdg_news.ui.ui_resource.theme.MyTheme
import com.cdg.cdg_news.ui.ui_resource.theme.SmallFontSize
import com.cdg.cdg_news.util.datastore.MyDataStore
import dagger.hilt.android.AndroidEntryPoint
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStore: MyDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var finishLoad by remember {
                mutableStateOf(false)
            }

            var lightColorScheme by remember {
                mutableStateOf(LightColorSchemeBlue)
            }

            var darkColorScheme by remember {
                mutableStateOf(DarkColorSchemeBlue)
            }

            var typography by remember {
                mutableStateOf(MediumFontSize)
            }

            val modifiedSmallFontSize = SmallFontSize.copy(
                titleLarge = SmallFontSize.titleLarge.copy(fontSize = 16.ssp),
                bodyLarge = SmallFontSize.bodyLarge.copy(fontSize = 12.ssp),
                labelSmall = SmallFontSize.labelSmall.copy(fontSize = 8.ssp),
            )


            val modifiedMediumFontSize = MediumFontSize.copy(
                titleLarge = MediumFontSize.titleLarge.copy(fontSize = 20.ssp),
                bodyLarge = MediumFontSize.bodyLarge.copy(fontSize = 16.ssp),
                labelSmall = MediumFontSize.labelSmall.copy(fontSize = 12.ssp),
            )


            val modifiedLargeFontSize = LargeFontSize.copy(
                titleLarge = LargeFontSize.titleLarge.copy(fontSize = 24.ssp),
                bodyLarge = LargeFontSize.bodyLarge.copy(fontSize = 20.ssp),
                labelSmall = LargeFontSize.labelSmall.copy(fontSize = 16.ssp),
            )


            LaunchedEffect(Unit) {

                lightColorScheme = when (dataStore.themeId.first()) {
                    1 -> LightColorSchemeBlue
                    2 -> LightColorSchemeYellow
                    3 -> LightColorSchemeOrange
                    4 -> LightColorSchemePurple
                    else -> DarkColorSchemeBlue
                }

                darkColorScheme = when (dataStore.themeId.first()) {
                    1 -> DarkColorSchemeBlue
                    2 -> DarkColorSchemeYellow
                    3 -> DarkColorSchemeOrange
                    4 -> DarkColorSchemePurple
                    else -> DarkColorSchemeBlue
                }

                typography = when (dataStore.textSizeId.first()) {
                    1 -> modifiedSmallFontSize
                    2 -> modifiedMediumFontSize
                    3 -> modifiedLargeFontSize
                    else -> modifiedMediumFontSize
                }

                finishLoad = true
            }

            if (finishLoad) {
                MyTheme(
                    lightColorScheme,
                    darkColorScheme,
                    typography
                ) {
                    MainComposable()
                }
            }
        }
    }
}


@Composable
fun MainComposable() {
    val navController = rememberNavController()
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute?.route != null &&
                currentRoute.route != Routes.SplashScreen.route &&
                currentRoute.route != Routes.LandingScreen.route
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                ) {

                    bottomNavigationItems().forEachIndexed { index, navigationItem ->

                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                selectedTextColor = MaterialTheme.colorScheme.secondary,
                                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                indicatorColor = MaterialTheme.colorScheme.secondary
                            ),
                            selected = currentRoute.hierarchy.any {
                                navigationItem.route == it.route
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = "NavigationBottomViewIcon",
                                    modifier = Modifier.size(20.sdp)
                                )
                            },
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {

                                    popUpTo(navController.graph.findStartDestination().id) {
                                        navigationSelectedItem = index
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {

        NavGraph(
            it.calculateBottomPadding().value,
            navController,
        )
    }


}