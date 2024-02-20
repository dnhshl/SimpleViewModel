package com.example.main.model

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "settings")

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val dataStore = application.dataStore

    val myInt = mutableStateOf(0)
    val myStringList = mutableStateOf(emptyList<String>())
    val myBoolean = mutableStateOf(false)

    private var _someTestData = "Hello ViewModel"
    val someTestData: String get() = _someTestData


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
    private val _clickCounter = MutableStateFlow(0)
    val clickCounter: StateFlow<Int> get() = _clickCounter.asStateFlow()

    fun incrementClickCounter() {
        _clickCounter.value++
        Log.i(">>>", "clickCounter = ${_clickCounter.value}")
    }
 */
}
