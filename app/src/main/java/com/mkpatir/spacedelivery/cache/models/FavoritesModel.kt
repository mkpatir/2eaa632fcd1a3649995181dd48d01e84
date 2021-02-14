package com.mkpatir.spacedelivery.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesModel(
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "coordinateX")
    var coordinateX: Float,
    @ColumnInfo(name = "coordinateY")
    var coordinateY: Float,
    @ColumnInfo(name = "capacity")
    var capacity: Int
)