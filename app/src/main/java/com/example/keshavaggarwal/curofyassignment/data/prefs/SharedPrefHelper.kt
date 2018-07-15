package com.example.keshavaggarwal.curofyassignment.data.prefs

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by KeshavAggarwal on 15/07/18.
 */
class SharedPrefHelper @Inject
constructor(val sp : SharedPreferences): SharedPrefInterface {

    companion object {
        const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN"
    }

    override fun getUserLoggedIn() = sp.getBoolean(PREF_KEY_USER_LOGGED_IN_MODE, false)

    override fun setUserLoggedIn(LoggedIn: Boolean) = sp.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE, true).apply()

}