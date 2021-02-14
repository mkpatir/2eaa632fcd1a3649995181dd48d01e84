package com.mkpatir.spacedelivery.api.models

import com.google.gson.annotations.SerializedName

data class SpaceStationModel(
     @SerializedName("name") val name: String?,
     @SerializedName("coordinateX") val coordinateX: Float?,
     @SerializedName("coordinateY") val coordinateY: Float?,
     @SerializedName("capacity") val capacity: Int?,
     @SerializedName("stock") var stock: Int?,
     @SerializedName("need") var need: Int?,
     var isFavorite: Boolean = false,
     var isTravelNotPossible: Boolean = false
)