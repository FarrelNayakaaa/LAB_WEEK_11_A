package com.example.lab_week_11_a

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsStore: SettingsStore) : ViewModel() {

    private val _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String> get() = _textLiveData

    init {
        // Collect Flow secara async
        viewModelScope.launch {
            settingsStore.text.collect { value ->
                _textLiveData.postValue(value)
            }
        }
    }

    fun saveText(text: String) {
        viewModelScope.launch {
            settingsStore.saveText(text)
        }
    }
}
