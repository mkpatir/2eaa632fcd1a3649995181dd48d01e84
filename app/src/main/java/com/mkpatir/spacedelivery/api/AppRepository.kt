package com.mkpatir.spacedelivery.api

import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getSpaceStations(): Flow<ArrayList<SpaceStationModel>>

}