package com.mkpatir.spacedelivery.internal.extension

import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import com.mkpatir.spacedelivery.cache.models.FavoritesModel
import kotlin.math.pow
import kotlin.math.sqrt

fun Int?.oneOrThis(): Int = this?.let { if (this < 1) 1 else this } ?: 1

fun Int?.isNullOrZero(): Boolean = this?.let { this == 0 } ?: true

fun Int?.orZero(): Int = this ?: 0

fun Float?.orZero(): Float = this ?: 0f

fun SpaceStationModel.distanceBetweenCurrentLocation(currentLocationX: Float,currentLocationY: Float): Int{
    return sqrt((coordinateX.orZero() - currentLocationX).pow(2) + (coordinateY.orZero() - currentLocationY).pow(2)).toInt()
}

fun SpaceStationModel.toFavoriteModel(): FavoritesModel = FavoritesModel(
    name = name.orEmpty(),
    coordinateX = coordinateX.orZero(),
    coordinateY = coordinateY.orZero(),
    capacity = capacity.orZero()
)

fun FavoritesModel.distanceBetweenOrigin(): Int{
    return sqrt((coordinateX.orZero() - 0f).pow(2) + (coordinateY.orZero() - 0f).pow(2)).toInt()
}