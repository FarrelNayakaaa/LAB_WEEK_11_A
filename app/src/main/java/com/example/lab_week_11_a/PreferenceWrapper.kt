package com.example.lab_week_11_a

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PreferenceWrapper(private val sharedPreferences: SharedPreferences) {

    private val textLiveData = MutableLiveData<String>()

    // keep listener reference so GC doesn't collect it and so we can unregister if needed
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            KEY_TEXT -> {
                textLiveData.postValue(sharedPreferences.getString(KEY_TEXT, ""))
            }
        }
    }

    init {
        // register listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        // initialize current value
        textLiveData.postValue(sharedPreferences.getString(KEY_TEXT, ""))
    }

    fun saveText(text: String) {
        sharedPreferences.edit()
            .putString(KEY_TEXT, text)
            .apply()
    }

    fun getText(): LiveData<String> {
        // ensure current value is posted
        textLiveData.postValue(sharedPreferences.getString(KEY_TEXT, ""))
        return textLiveData
    }

    companion object {
        const val KEY_TEXT = "keyText"
    }
}
