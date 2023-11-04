package com.ptk.ptk_news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ptk.ptk_news.model.ui_model.BottomNavigationItem
import com.ptk.ptk_news.model.ui_model.bottomNavigationItems
import com.ptk.ptk_news.ui.ui_resource.navigation.NavGraph
import com.ptk.ptk_news.ui.ui_resource.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MainComposable()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("TESTASDF", "ONBACKPRESS")

    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                bottomNavigationItems().forEachIndexed { index, navigationItem ->
                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = currentRoute?.hierarchy?.any {
                            navigationItem.route == it.route
                        } == true,
                        label = {
                            Text(navigationItem.title)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = "NavigationBottomViewIcon"
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
            navController
        )
    }

}