package com.example.modulo10

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        loadLocale()
        setContentView(R.layout.activity_main)

        findViewById<RadioGroup>(R.id.radioGroupLanguage).apply {
            setOnCheckedChangeListener { _, checkedId ->
                setLocale(when (checkedId) {
                    R.id.radioEnglish -> "en"
                    R.id.radioSpanish -> "es"
                    R.id.radioPortuguese -> "pt"
                    else -> return@setOnCheckedChangeListener
                })
            }
            check(getCheckedRadioId())
        }
    }

    private fun setLocale(languageCode: String) {
        if (sharedPreferences.getString("App_Language", "pt") != languageCode) {
            updateLocale(languageCode)
            sharedPreferences.edit().putString("App_Language", languageCode).apply()
            recreate()
        }
    }

    private fun loadLocale() {
        updateLocale(sharedPreferences.getString("App_Language", "pt") ?: "pt")
    }

    private fun updateLocale(languageCode: String) {
        Locale.setDefault(Locale(languageCode))
        resources.configuration.setLocale(Locale(languageCode))
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
    }

    private fun getCheckedRadioId(): Int {
        return when (sharedPreferences.getString("App_Language", "pt")) {
            "en" -> R.id.radioEnglish
            "es" -> R.id.radioSpanish
            "pt" -> R.id.radioPortuguese
            else -> R.id.radioPortuguese // Default fallback
        }
    }
}
