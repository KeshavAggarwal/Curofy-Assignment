package com.example.keshavaggarwal.curofyassignment.data.prefs

/**
 * Created by KeshavAggarwal on 15/07/18.
 */
interface SharedPrefInterface {

    fun getUserLoggedIn() : Boolean

    fun setUserLoggedIn(LoggedIn : Boolean)


}