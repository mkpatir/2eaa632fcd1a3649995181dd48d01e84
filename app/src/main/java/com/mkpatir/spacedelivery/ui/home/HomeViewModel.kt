package com.mkpatir.spacedelivery.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mkpatir.spacedelivery.api.AppRepository
import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import com.mkpatir.spacedelivery.internal.utils.PreferenceHelper
import com.mkpatir.spacedelivery.ui.base.BaseViewModel

class HomeViewModel(
    private val appRepository: AppRepository,
    private val preferenceHelper: PreferenceHelper
): BaseViewModel() {

    val ugsValue = ObservableField(0)
    val eusValue = ObservableField(0)
    val dsValue = ObservableField(0)
    val spaceshipName = ObservableField("")
    val damageCapacity = ObservableField(0)

    val spaceStationsLiveData = MutableLiveData<ArrayList<SpaceStationModel>>()

    init {
        callService(appRepository.getSpaceStations()) {
            spaceStationsLiveData.postValue(it)
        }

        preferenceHelper.spaceship?.let {
            ugsValue.set(it.materialCapacity * UGS_VARIABLE)
            eusValue.set(it.speed * EUS_VARIABLE)
            dsValue.set(it.durability * DS_VARIABLE)
            spaceshipName.set(it.name)
            damageCapacity.set(it.damageCapacity)
        }
    }

    companion object {
        private const val UGS_VARIABLE = 10000
        private const val EUS_VARIABLE = 200
        private const val DS_VARIABLE = 10000
    }

}