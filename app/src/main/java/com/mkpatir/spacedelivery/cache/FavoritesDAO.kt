package com.mkpatir.spacedelivery.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mkpatir.spacedelivery.cache.models.FavoritesModel

@Dao
interface FavoritesDAO {

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<FavoritesModel>

    @Insert
    fun addToFavorites(favoritesModel: FavoritesModel)

    @Delete
    fun removeFromFavorites(favoritesModel: FavoritesModel)

}