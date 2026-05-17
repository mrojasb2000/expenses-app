package com.bramworks.tech.expenses.ui

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.bramworks.tech.expenses.R
import com.google.android.material.appbar.MaterialToolbar
import java.util.Locale

object LanguageMenuHelper {

    fun setupToolbar(
        activity: AppCompatActivity,
        toolbar: MaterialToolbar,
        titleRes: Int,
        showBackButton: Boolean = false,
    ) {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            setTitle(titleRes)
            setDisplayHomeAsUpEnabled(showBackButton)
        }
    }

    fun inflateMenu(menuInflater: MenuInflater, menu: Menu) {
        menuInflater.inflate(R.menu.language_menu, menu)
    }

    fun syncSelectedLanguage(menu: Menu) {
        val language = currentLanguageCode()
        menu.findItem(R.id.action_language_english)?.isChecked = language == ENGLISH_LANGUAGE
        menu.findItem(R.id.action_language_spanish)?.isChecked = language == SPANISH_LANGUAGE
    }

    fun handleLanguageSelection(itemId: Int): Boolean {
        return when (itemId) {
            R.id.action_language_english -> {
                updateLanguageIfNeeded(ENGLISH_LANGUAGE)
                true
            }
            R.id.action_language_spanish -> {
                updateLanguageIfNeeded(SPANISH_LANGUAGE)
                true
            }
            else -> false
        }
    }

    private fun updateLanguageIfNeeded(languageCode: String) {
        if (currentLanguageCode() == languageCode) return
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }

    private fun currentLanguageCode(): String {
        val appLocales = AppCompatDelegate.getApplicationLocales()
        val locale = if (appLocales.isEmpty) {
            LocaleListCompat.getAdjustedDefault()[0]
        } else {
            appLocales[0]
        }

        return locale?.language?.lowercase(Locale.ROOT).orEmpty()
    }

    private const val ENGLISH_LANGUAGE = "en"
    private const val SPANISH_LANGUAGE = "es"
}

