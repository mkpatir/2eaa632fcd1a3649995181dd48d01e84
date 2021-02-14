package com.mkpatir.spacedelivery.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import com.mkpatir.spacedelivery.internal.extension.distanceBetweenCurrentLocation
import com.mkpatir.spacedelivery.internal.extension.getCompatDrawable

data class StationViewState(
    val context: Context,
    val spaceStationModel: SpaceStationModel,
    val currentLocationX: Float,
    val currentLocationY: Float
) {

    fun getStationName(): String = spaceStationModel.name.orEmpty()

    fun getStock(): String = context.getString(R.string.space_station_need_capacity,spaceStationModel.stock,spaceStationModel.capacity)

    fun getDistance(): String = context.getString(R.string.space_station_distance,spaceStationModel.distanceBetweenCurrentLocation(currentLocationX,currentLocationY))

    fun getFavoriteDrawable(): Drawable? = if (spaceStationModel.isFavorite) context.getCompatDrawable(R.drawable.ic_favorite_full) else context.getCompatDrawable(R.drawable.ic_favorite_empty)

    fun isTravelPossible(): Boolean = spaceStationModel.isTravelNotPossible.not()

}