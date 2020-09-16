package com.amary.codexgamer.utils

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {

    private var pref: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    private var editor: SharedPreferences.Editor = pref.edit()

    companion object{
        private const val PREFS_NAME = "com.amary.codexgamer.ui.settings"
        const val LANGUAGE = "language"
        const val DARK_MODE = "dark_mode"
    }


    fun setDataLanguage(value: String) = editor.putString(LANGUAGE, value).commit()
    fun getDataLanguage() = pref.getString(LANGUAGE, "")

    fun setDataDarkMode(value: Int) = editor.putInt(DARK_MODE, value).commit()
    fun getDataDarkMode(): Int? {
        return pref.getInt(DARK_MODE, 0)
    }

}