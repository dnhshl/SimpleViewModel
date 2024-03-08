package com.example.main.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "settings")

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val dataStore = application.dataStore

    // clickCounter via Datastore
    private val clickCounterKey = intPreferencesKey("click_counter_key")

    val clickCounter = dataStore.data
        .map { preferences ->
            preferences[clickCounterKey] ?: 0
        }

    fun incrementAndSaveClickCounter() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val currentValue = preferences[clickCounterKey] ?: 0
                preferences[clickCounterKey] = currentValue + 1
            }
        }
    }



/*
    // Implementation of clickCounter without Datastore

    private val _clickCounter = MutableStateFlow(0)
    val clickCounter: StateFlow<Int> get() = _clickCounter.asStateFlow()

    fun incrementClickCounter() {
        _clickCounter.value++
        Log.i(">>>", "clickCounter = ${_clickCounter.value}")
    }
 */

    // Snackbar
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    private var _snackbarDuration = SnackbarDuration.Indefinite
    val snackbarDuration get() = _snackbarDuration

    private var _snackbarAction: String? = null
    val snackbarAction get() = _snackbarAction

    private var _snackbarDismissable = true
    val snackbarDismissable get() = _snackbarDismissable

    private var _snackbarOnAction: () -> Unit = {}
    val snackbarOnAction get()=_snackbarOnAction

    private var _snackbarOnDismiss: () -> Unit = {}
    val snackbarOnDismiss get()=_snackbarOnDismiss

    fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        dismissable: Boolean = true,
        duration: SnackbarDuration = SnackbarDuration.Indefinite,
        onAction: () -> Unit = {},
        onDismiss: () -> Unit = {},
        showAgain: Boolean = false
    ) {
        // set Snackbar
        _snackbarMessage.value = message
        _snackbarDismissable = dismissable
        _snackbarAction = actionLabel
        _snackbarDuration = duration
        _snackbarOnAction = onAction
        _snackbarOnDismiss = onDismiss
    }


    fun resetSnackbar() {
        _snackbarMessage.value = null
    }


}
