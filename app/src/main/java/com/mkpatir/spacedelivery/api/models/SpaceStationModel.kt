package com.mkpatir.spacedelivery.api.models

import com.google.gson.annotations.SerializedName

data class SpaceStationModel(
     @SerializedName("name") val name: String?,
     @SerializedName("coordinateX") val coordinateX: Float?,
     @SerializedName("coordinateY") val coordinateY: Float?,
     @SerializedName("capacity") val capacity: Int?,
     @SerializedName("stock") val stock: Int?,
     @SerializedName("need") val need: Int?,
     var isFavorite: Boolean = false
)