package com.example.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.main.model.MainViewModel
import com.example.main.ui.theme.SimpleViewModelTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleViewModelTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    var showMenu by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    // show Snackbar when snackbarMessage changes. All controlled via
    // viewModel.showSnackbar().
    val snackbarMessage by viewModel.snackbarMessage.collectAsState(null)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState){
            Snackbar(
                snackbarData = it,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionColor = MaterialTheme.colorScheme.onPrimaryContainer,
                dismissActionContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } },
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            MyTopBar(
                navController = navController,
                screens = navDestinations,
                onMenuClick = { showMenu = !showMenu }
            )
        },
        bottomBar = {
            MyNavBar(
                navController = navController,
                screens = navDestinations
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScreenInfo.HomeScreen.route,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            navDestinations.forEach { screen ->
                composable(screen.route) {
                    screen.content(navController, viewModel)
                }
            }
        }
        MyMenu(
            showMenu = showMenu,
            navController = navController,
            paddingValues = paddingValues,
            onToggleMenu = { showMenu = !showMenu }
        )
    }

    // display Snackbar (executed on viewModel.showSnackbar())
    LaunchedEffect(snackbarMessage) {
        Log.i(">>>>", "LaunchedEffect $snackbarMessage")
        snackbarMessage?.let {
            val result = snackbarHostState
                .showSnackbar(
                    message = it,
                    actionLabel = viewModel.snackbarAction,
                    withDismissAction = viewModel.snackbarDismissable,
                    // Defaults to SnackbarDuration.Short
                    duration = viewModel.snackbarDuration
                )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    Log.i(">>>>","Snackbar Action")
                    viewModel.snackbarOnAction.invoke()
                    viewModel.resetSnackbar()
                }
                SnackbarResult.Dismissed -> {
                    Log.i(">>>>","Snackbar Dismiss")
                    viewModel.snackbarOnDismiss.invoke()
                    viewModel.resetSnackbar()
                }
            }
        }
    }
}


