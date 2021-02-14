package com.mkpatir.spacedelivery.api

import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import retrofit2.http.GET

interface AppService {

    @GET("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getSpaceStations(): ArrayList<SpaceStationModel>


}