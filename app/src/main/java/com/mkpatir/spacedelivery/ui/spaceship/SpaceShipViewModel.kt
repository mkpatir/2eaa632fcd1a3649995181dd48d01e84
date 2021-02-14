package com.mkpatir.spacedelivery.ui.spaceship

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mkpatir.spacedelivery.internal.extension.isNullOrZero
import com.mkpatir.spacedelivery.internal.extension.oneOrThis
import com.mkpatir.spacedelivery.internal.extension.orZero
import com.mkpatir.spacedelivery.internal.utils.PreferenceHelper
import com.mkpatir.spacedelivery.models.CheckProperty
import com.mkpatir.spacedelivery.models.SpaceShip
import com.mkpatir.spacedelivery.models.SpaceShipProperty
import com.mkpatir.spacedelivery.ui.base.BaseViewModel

class SpaceShipViewModel(
    private val preferenceHelper: PreferenceHelper
): BaseViewModel() {

    val durability = ObservableField(0)
    val speed = ObservableField(0)
    val capacity = ObservableField(0)

    val totalPoint = ObservableField(PROPERTY_MAX_COUNT)
    val durabilityMaxProgress = ObservableField(SEEK_BAR_MAX_PROGRESS_COUNT)
    val speedMaxProgress = ObservableField(SEEK_BAR_MAX_PROGRESS_COUNT)
    val capacityMaxProgress = ObservableField(SEEK_BAR_MAX_PROGRESS_COUNT)

    val checkPropertyLiveData = MutableLiveData<CheckProperty>()

    fun updateProperties(progress: Int,property: SpaceShipProperty){
        when(property){
            SpaceShipProperty.DURABILITY -> {
                durability.set(progress)
                setMaxProgressCount(speed.get() ?: 0,durability.get().oneOrThis(),capacity.get().oneOrThis(),speedMaxProgress)
                setMaxProgressCount(capacity.get() ?: 0,durability.get().oneOrThis(),speed.get().oneOrThis(),capacityMaxProgress)
            }
            SpaceShipProperty.SPEED -> {
                speed.set(progress)
                setMaxProgressCount(durability.get() ?: 0,speed.get().oneOrThis(),capacity.get().oneOrThis(),durabilityMaxProgress)
                setMaxProgressCount(capacity.get() ?: 0,durability.get().oneOrThis(),speed.get().oneOrThis(),capacityMaxProgress)
            }
            SpaceShipProperty.CAPACITY -> {
                capacity.set(progress)
                setMaxProgressCount(durability.get() ?: 0,speed.get().oneOrThis(),capacity.get().oneOrThis(),durabilityMaxProgress)
                setMaxProgressCount(speed.get() ?: 0,durability.get().oneOrThis(),capacity.get().oneOrThis(),speedMaxProgress)
            }
        }
        totalPoint.set((PROPERTY_MAX_COUNT - (durability.get().orZero() + speed.get().orZero() + capacity.get().orZero())))
    }

    fun checkProperties(name: String){
        if (durability.get().isNullOrZero() || speed.get().isNullOrZero() || capacity.get().isNullOrZero() || name.isEmpty()){
            checkPropertyLiveData.postValue(CheckProperty.MISSING_PROPERTY)
        }
        else if ((totalPoint.get() ?: 0) > 0){
            checkPropertyLiveData.postValue(CheckProperty.CONTINUE_WITH_MISSING_COUNT)
        }
        else {
            preferenceHelper.spaceship = SpaceShip(name,speed.get().orZero(),capacity.get().orZero(),durability.get().orZero())
            checkPropertyLiveData.postValue(CheckProperty.CONTINUE)
        }
    }

    private fun setMaxProgressCount(calculatedPropertyCount: Int,otherPropertyOne: Int,otherPropertyTwo: Int,updatedObservableField: ObservableField<Int>){
        val count = (PROPERTY_MAX_COUNT - (calculatedPropertyCount + otherPropertyOne + otherPropertyTwo)) + calculatedPropertyCount
        updatedObservableField.set(count)
    }

    companion object {
        private const val PROPERTY_MAX_COUNT = 15
        private const val SEEK_BAR_MAX_PROGRESS_COUNT = 13
    }

}