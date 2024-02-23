package com.example.simplenav.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.model.MainViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val clickCounter by viewModel.clickCounter.collectAsState(0)

    Log.i(">>>>", "clickCounter in HomeScreen $clickCounter")

    if (clickCounter%5 == 1) viewModel.showSnackbar(
        message ="clickCounter reached $clickCounter",
        actionLabel = "OK",
        duration = SnackbarDuration.Long,
        onAction = { Log.i(">>>>","onAction in Snackbar") },
        onDismiss = { viewModel.incrementAndSaveClickCounter() }
        )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(clickCounter.toString(), fontSize =  24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.incrementAndSaveClickCounter()
        }) {
            Text("Klick mich")
        }
    }
}
