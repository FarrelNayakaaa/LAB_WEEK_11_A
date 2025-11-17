package com.example.lab_week_11_a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class MainActivity : AppCompatActivity() {

    private lateinit var preferenceViewModel: PreferenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get PreferenceWrapper from Application
        val preferenceWrapper = (application as PreferenceApplication).preferenceWrapper

        // ViewModelFactory yang aman
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PreferenceViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return PreferenceViewModel(preferenceWrapper) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        preferenceViewModel = ViewModelProvider(this, factory)[PreferenceViewModel::class.java]

        val tv = findViewById<TextView>(R.id.activity_main_text_view)
        val et = findViewById<EditText>(R.id.activity_main_edit_text)
        val btn = findViewById<Button>(R.id.activity_main_button)

        // Observe LiveData
        preferenceViewModel.getText().observe(this) { text ->
            tv.text = text ?: ""
        }

        btn.setOnClickListener {
            val input = et.text.toString()
            preferenceViewModel.saveText(input)
        }
    }
}
