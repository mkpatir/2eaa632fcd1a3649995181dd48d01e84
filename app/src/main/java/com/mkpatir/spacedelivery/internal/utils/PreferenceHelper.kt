package com.mkpatir.spacedelivery.internal.utils

import android.content.Context
import com.google.gson.Gson
import com.mkpatir.spacedelivery.models.SpaceShip

class PreferenceHelper(context: Context) {

    companion object {
        private const val APP_NAME = "com.mkpatir.spacedelievry"
        private const val SPACESHIP = "spaceship"
    }

    private val sharedPref = context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE)

    var spaceship: SpaceShip?
        get() = getJsonFromSharedPref(sharedPref.getString(SPACESHIP,""))
        set(value) = saveJsonToSharedPref<SpaceShip>(value)

    private inline fun <reified T> getJsonFromSharedPref(data: String?): T{
        val gSon = Gson()
        return gSon.fromJson(data,T::class.java)
    }

    private inline fun <reified T> saveJsonToSharedPref(spaceShip: SpaceShip?){
        val gSon = Gson()
        val data = gSon.toJson(spaceShip)
        sharedPref.edit().putString(SPACESHIP,data).apply()
    }

}