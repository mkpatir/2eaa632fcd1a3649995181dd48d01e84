package com.mkpatir.spacedelivery.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import com.mkpatir.spacedelivery.internal.extension.getCompatDrawable

data class StationViewState(
    val context: Context,
    val spaceStationModel: SpaceStationModel
) {

    fun getStationName(): String = spaceStationModel.name.orEmpty()

    fun getNeed(): String = context.getString(R.string.space_station_need_capacity,spaceStationModel.need,spaceStationModel.capacity)

    fun getDistance(): String = context.getString(R.string.space_station_distance,0)

    fun getFavoriteDrawable(): Drawable? = if (spaceStationModel.isFavorite) context.getCompatDrawable(R.drawable.ic_favorite_full) else context.getCompatDrawable(R.drawable.ic_favorite_empty)

}