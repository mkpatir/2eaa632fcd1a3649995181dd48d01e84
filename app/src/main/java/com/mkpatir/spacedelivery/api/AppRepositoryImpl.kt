package com.mkpatir.spacedelivery.api

import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AppRepositoryImpl(private val appService: AppService): AppRepository {

    override fun getSpaceStations(): Flow<ArrayList<SpaceStationModel>> = flow { emit(appService.getSpaceStations()) }

}