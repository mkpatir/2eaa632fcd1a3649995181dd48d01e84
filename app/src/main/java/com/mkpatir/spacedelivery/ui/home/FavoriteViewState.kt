package com.mkpatir.spacedelivery.ui.home

import android.content.Context
import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.cache.models.FavoritesModel
import com.mkpatir.spacedelivery.internal.extension.distanceBetweenOrigin

data class FavoriteViewState(
    private val context: Context,
    private val favoritesModel: FavoritesModel
) {

    fun getStationName(): String = favoritesModel.name

    fun getDistance(): String = context.getString(R.string.space_station_distance,favoritesModel.distanceBetweenOrigin())

    fun getCapacity(): String = context.getString(R.string.space_station_capacity,favoritesModel.capacity)

}