package com.example.simplenav.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
fun DetailsScreen(
    navController: NavController,
    viewModel: MainViewModel
    ) {

    val clickCounter by viewModel.clickCounter.collectAsState(0)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(clickCounter.toString(), fontSize =  24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.incrementAndSaveClickCounter() }) {
            Text("Klick weiter")
        }
    }
}
