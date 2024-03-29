package ru.sashel007.quitsmoking.util

import android.content.Context
import android.content.SharedPreferences

class MySharedPref(context: Context) {

    companion object {
        private const val SHARED_PREF_NAME = "ApplicationFirstLaunching"
        private const val IS_FIRST_LAUNCH = "isFirstLaunched"
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun setFirstLaunch(isFirstLaunch: Boolean) {
        val editor = sharedPreferences.edit()
        with(editor) {
            putBoolean(IS_FIRST_LAUNCH, isFirstLaunch)
            apply()
        }
    }

    fun checkIsFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)
    }

}