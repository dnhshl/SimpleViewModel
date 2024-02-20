package com.example.main

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.main.model.MainViewModel
import com.example.simplenav.ui.screens.DetailsScreen
import com.example.simplenav.ui.screens.HomeScreen

import com.example.simplenav.ui.screens.SettingsScreen

sealed class ScreenInfo(
    val route: String,
    val title: Int,
    val label: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val content: @Composable (NavController, MainViewModel) -> Unit
) {
    object HomeScreen : ScreenInfo(
        route = "home",
        title = R.string.homeScreenTitle,
        label = R.string.homeScreenLabel,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon =Icons.Outlined.Home,
        content = { navController, viewModel -> HomeScreen(navController, viewModel) }
    )

    object DetailsScreen : ScreenInfo(
        route = "detail",
        title = R.string.detailsScreenTitle,
        label = R.string.detailsScreenLabel,
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle,
        content = { navController, viewModel -> DetailsScreen(navController, viewModel) }
    )

    object SettingsScreen : ScreenInfo(
        route = "settings",
        title = R.string.settingsScreenTitle,
        label = R.string.settingsScreenLabel,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        content = { navController, viewModel -> SettingsScreen(navController, viewModel) }
    )
}


val navDestinations = listOf (
    ScreenInfo.HomeScreen,
    ScreenInfo.DetailsScreen,
    ScreenInfo.SettingsScreen,
)

@Composable
fun MyNavBar(
    navController: NavController,
    screens: List<ScreenInfo>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar{
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route ==  screen.route} == true,
                onClick = {
                    navigateTo(screen.route, navController)
                },
                icon = {
                    Icon(
                        if (currentDestination?.route == screen.route) {
                            screen.selectedIcon
                        }
                        else screen.unselectedIcon,
                        contentDescription = stringResource(screen.title)
                    )
                },
                label = { Text(text = stringResource(screen.label)) },
                alwaysShowLabel = true
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    navController: NavController,
    screens: List<ScreenInfo>,
    onMenuClick: () ->Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    var screenTitle = ""

    screens.forEach { screen ->
        if (currentRoute == screen.route) screenTitle = stringResource(screen.title)
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(screenTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

fun navigateTo(route: String, navController: NavController) {

    Log.i(">>>>>", "navigateTo $route")
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

