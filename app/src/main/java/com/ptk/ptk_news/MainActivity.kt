package com.ptk.ptk_news

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.ptk.ptk_news.model.ui_model.bottomNavigationItems
import com.ptk.ptk_news.ui.ui_resource.navigation.NavGraph
import com.ptk.ptk_news.ui.ui_resource.theme.DarkColorSchemeBlue
import com.ptk.ptk_news.ui.ui_resource.theme.DarkColorSchemeOrange
import com.ptk.ptk_news.ui.ui_resource.theme.DarkColorSchemePurple
import com.ptk.ptk_news.ui.ui_resource.theme.DarkColorSchemeYellow
import com.ptk.ptk_news.ui.ui_resource.theme.LightColorSchemeBlue
import com.ptk.ptk_news.ui.ui_resource.theme.LightColorSchemeOrange
import com.ptk.ptk_news.ui.ui_resource.theme.LightColorSchemePurple
import com.ptk.ptk_news.ui.ui_resource.theme.LightColorSchemeYellow
import com.ptk.ptk_news.ui.ui_resource.theme.MyTheme
import com.ptk.ptk_news.util.datastore.MyDataStore
import dagger.hilt.android.AndroidEntryPoint
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var datastore: MyDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var lightColorScheme by remember {
                mutableStateOf(LightColorSchemeOrange)
            }
            var darkColorScheme by remember {
                mutableStateOf(DarkColorSchemeOrange)
            }
            LaunchedEffect(Unit) {
                lightColorScheme = when (datastore.themeId.first()) {
                    1 -> LightColorSchemeOrange
                    2 -> LightColorSchemeYellow
                    3 -> LightColorSchemeBlue
                    4 -> LightColorSchemePurple
                    else -> LightColorSchemeOrange
                }
                darkColorScheme = when (datastore.themeId.first()) {
                    1 -> DarkColorSchemeOrange
                    2 -> DarkColorSchemeYellow
                    3 -> DarkColorSchemeBlue
                    4 -> DarkColorSchemePurple
                    else -> DarkColorSchemeOrange
                }
            }

            MyTheme(
                lightColorScheme,
                darkColorScheme
            ) {
                MainComposable()
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
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                //getting the list of bottom navigation items for our data class
                bottomNavigationItems().forEachIndexed { index, navigationItem ->
                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        ),
                        selected = currentRoute?.hierarchy?.any {
                            navigationItem.route == it.route
                        } == true,
                        label = {
                            Text(
                                navigationItem.title,
                                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            )
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = "NavigationBottomViewIcon",
                                modifier = Modifier.size(25.sdp)
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    navigationSelectedItem = index

                                    saveState = true
                                    Log.e("TESTASDF", navigationSelectedItem.toString())

                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
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