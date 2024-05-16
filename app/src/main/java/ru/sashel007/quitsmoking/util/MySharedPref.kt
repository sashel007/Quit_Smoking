package ru.sashel007.quitsmoking.util

import android.content.Context
import android.content.SharedPreferences
import ru.sashel007.quitsmoking.mainscreen.elements.NavRoutes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPref @Inject constructor(context: Context) {

    companion object {
        private const val SHARED_PREF_NAME = "ApplicationFirstLaunching"
        private const val IS_FIRST_LAUNCH = "isFirstLaunched"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val lastOpenedPage: String = "lastOpenedPage"

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

    fun setLastOpenedPage(page: String) {
        val editor = sharedPreferences.edit()
        with(editor) {
            putString(lastOpenedPage, page)
            apply()
        }
    }

    fun getLastOpenedPage(): String? {
        return sharedPreferences.getString(lastOpenedPage, NavRoutes.STARTING_PAGE)
    }

}