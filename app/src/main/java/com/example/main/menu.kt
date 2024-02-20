package com.example.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController



@Composable
fun MyMenu( showMenu: Boolean = false,
            navController: NavController,
            paddingValues: PaddingValues,
            onToggleMenu: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        // DropdownMenu composable
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { onToggleMenu() }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.menuItem1)) },
                onClick = {
                    Log.i(">>>>", "Menu Item 1 geklickt")
                    onToggleMenu()
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.menuItem2)) },
                onClick = {
                    Log.i(">>>>", "Menu Item 2 geklickt")
                    onToggleMenu()
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.menuItem3)) },
                onClick = {
                    Log.i(">>>>", "Menu Item 3 geklickt")
                    onToggleMenu()
                    navigateTo(ScreenInfo.HomeScreen.route, navController)
                }
            )
        }
    }
}