package com.example.lab_week_11_a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsStore = (application as SettingsApplication).settingsStore

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SettingsViewModel(settingsStore) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        viewModel = ViewModelProvider(this, factory)[SettingsViewModel::class.java]

        val tv = findViewById<TextView>(R.id.activity_main_text_view)
        val et = findViewById<EditText>(R.id.activity_main_edit_text)
        val btn = findViewById<Button>(R.id.activity_main_button)

        // Observe LiveData
        viewModel.textLiveData.observe(this) { value ->
            tv.text = value ?: ""
        }

        btn.setOnClickListener {
            viewModel.saveText(et.text.toString())
        }
    }
}
