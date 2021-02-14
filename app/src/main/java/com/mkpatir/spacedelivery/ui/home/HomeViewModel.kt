package com.mkpatir.spacedelivery.ui.home

import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mkpatir.spacedelivery.api.AppRepository
import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import com.mkpatir.spacedelivery.cache.DBManager
import com.mkpatir.spacedelivery.cache.models.FavoritesModel
import com.mkpatir.spacedelivery.internal.extension.distanceBetweenCurrentLocation
import com.mkpatir.spacedelivery.internal.extension.orZero
import com.mkpatir.spacedelivery.internal.extension.toFavoriteModel
import com.mkpatir.spacedelivery.internal.utils.PreferenceHelper
import com.mkpatir.spacedelivery.models.TravelEndReason
import com.mkpatir.spacedelivery.ui.base.BaseViewModel
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(
    private val appRepository: AppRepository,
    private val preferenceHelper: PreferenceHelper,
    private val dbManager: DBManager?
): BaseViewModel() {

    private var damageTimer: CountDownTimer? = null
    private var searchTimer: CountDownTimer? = null
    private var damageTimerMillisUntilFinished: Long = 0L
    private var damageTimerIsPaused = false

    val ugsValue = ObservableField(0)
    val eusValue = ObservableField(0)
    val dsValue = ObservableField(0)
    val spaceshipName = ObservableField("")
    val damageCapacity = ObservableField(0)
    val currentStation = ObservableField(WORLD)

    var currentLocationX: Float = 0f
    var currentLocationY: Float = 0f

    val spaceStationsLiveData = MutableLiveData<ArrayList<SpaceStationModel>>()
    val travelEndLiveData = MutableLiveData<TravelEndReason>()
    val damageTimeLiveData = MutableLiveData<Int>()
    val rvIndexLiveData = MutableLiveData<Int>()
    val favoritesLiveData = MutableLiveData<List<FavoritesModel>>()

    init {
        callService(appRepository.getSpaceStations()) { list ->
            list.find { item -> item.name == WORLD }?.let {
                list.remove(it)
            }

            list.forEach {
                if (getAllFavorites()?.contains(it.toFavoriteModel()) == true){
                    it.isFavorite = true
                }
            }

            spaceStationsLiveData.postValue(list)
            startDamageTimer(dsValue.get().orZero().toLong())
        }

        preferenceHelper.spaceship?.let {
            ugsValue.set(it.materialCapacity * UGS_VARIABLE)
            eusValue.set(it.speed * EUS_VARIABLE)
            dsValue.set(it.durability * DS_VARIABLE)
            spaceshipName.set(it.name)
            damageCapacity.set(it.damageCapacity)
            damageTimeLiveData.postValue(it.durability * DAMAGE_TIME_COUNT)
        }

        getAllFavorites()?.let {
            favoritesLiveData.postValue(it)
        }
    }

    private fun getAllFavorites(): List<FavoritesModel>? = dbManager?.favoritesDAO()?.getFavorites()

    fun travelToSpaceStation(spaceStationModel: SpaceStationModel){
        val tempList = spaceStationsLiveData.value
        when {
            spaceStationModel.need.orZero() > ugsValue.get().orZero() -> {
                spaceStationsLiveData.value?.forEachIndexed {  index, item ->
                    if (item.name == spaceStationModel.name){
                        tempList?.get(index)?.need = item.need.orZero() - ugsValue.get().orZero()
                        tempList?.get(index)?.stock = item.stock.orZero() + ugsValue.get().orZero()
                    }
                }
                ugsValue.set(0)
                eusValue.set(eusValue.get().orZero() - spaceStationModel.distanceBetweenCurrentLocation(currentLocationX, currentLocationY))
                setTravelEnd(tempList,TravelEndReason.UGS_OVER)
            }
            spaceStationModel.distanceBetweenCurrentLocation(currentLocationX,currentLocationY) == eusValue.get().orZero() -> {
                spaceStationsLiveData.value?.forEachIndexed {  index, item ->
                    if (item.name == spaceStationModel.name){
                        tempList?.get(index)?.need = 0
                        tempList?.get(index)?.stock = spaceStationModel.capacity
                    }
                }
                ugsValue.set(ugsValue.get().orZero() - spaceStationModel.need.orZero())
                eusValue.set(0)
                setTravelEnd(tempList,TravelEndReason.EUS_OVER)
            }
            else -> {
                ugsValue.set(ugsValue.get().orZero() - spaceStationModel.need.orZero())
                eusValue.set(eusValue.get().orZero() - spaceStationModel.distanceBetweenCurrentLocation(currentLocationX,currentLocationY))
                spaceStationsLiveData.value?.forEachIndexed { index, item ->
                    if (item.distanceBetweenCurrentLocation(currentLocationX, currentLocationY) > eusValue.get().orZero()){
                        tempList?.get(index)?.isTravelNotPossible = true
                    }

                    if (item.name == spaceStationModel.name){
                        tempList?.get(index)?.need = 0
                        tempList?.get(index)?.stock = spaceStationModel.capacity
                        tempList?.get(index)?.isTravelNotPossible = true
                    }
                }

                var bigEusCount = 0
                spaceStationsLiveData.value?.forEach {
                    if (it.distanceBetweenCurrentLocation(currentLocationX, currentLocationY) > eusValue.get().orZero()){
                        bigEusCount += 1
                    }
                }

                var possibleTravelCount = 0
                tempList?.forEach {
                    if (it.isTravelNotPossible.not()){
                        possibleTravelCount += 1
                    }
                }

                when {
                    bigEusCount == spaceStationsLiveData.value?.size -> {
                        setTravelEnd(tempList,TravelEndReason.INSUFFICIENT_EUS)
                    }
                    possibleTravelCount == 0 -> {
                        setTravelEnd(tempList,TravelEndReason.FINISHED_ALL_TRAVEL)
                    }
                    else -> {
                        currentLocationX = spaceStationModel.coordinateX.orZero()
                        currentLocationY = spaceStationModel.coordinateY.orZero()
                        currentStation.set(spaceStationModel.name.orEmpty())
                        spaceStationsLiveData.postValue(tempList)
                    }
                }
            }
        }
    }

    private fun setTravelEnd(list: ArrayList<SpaceStationModel>?,travelEndReason: TravelEndReason){
        damageTimer?.cancel()
        currentLocationX = 0f
        currentLocationY = 0f
        currentStation.set(WORLD)
        travelEndLiveData.postValue(travelEndReason)
        list?.forEach {
            it.isTravelNotPossible = true
        }
        spaceStationsLiveData.postValue(list)
    }

    fun addOrRemoveFavorite(spaceStationModel: SpaceStationModel){
        if (spaceStationModel.isFavorite){
            dbManager?.favoritesDAO()?.removeFromFavorites(spaceStationModel.toFavoriteModel())
        }
        else {
            dbManager?.favoritesDAO()?.addToFavorites(spaceStationModel.toFavoriteModel())
        }
        val tempList = spaceStationsLiveData.value
        spaceStationsLiveData.value?.forEachIndexed { index, item ->
            if (spaceStationModel.name == item.name){
                tempList?.get(index)?.isFavorite = item.isFavorite.not()
            }
        }
        spaceStationsLiveData.postValue(tempList)
        getAllFavorites()?.let {
            favoritesLiveData.postValue(it)
        }
    }

    fun searchSpaceStation(name: String){
        searchTimer?.cancel()
        searchTimer = null
        searchTimer = object : CountDownTimer(SEARCH_DELAY, SEARCH_DELAY){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                var index = -1
                spaceStationsLiveData.value?.indexOfFirst { item -> item.name.orEmpty().toLowerCase(Locale("tr")).contains(name) }?.let {
                    index = it
                }
                if (index != -1 && spaceStationsLiveData.value?.size.orZero() >= index){
                    rvIndexLiveData.postValue(index)
                }
            }
        }.start()
    }

    fun removeFromFavorites(favoritesModel: FavoritesModel){
        dbManager?.favoritesDAO()?.removeFromFavorites(favoritesModel)
        val tempList = spaceStationsLiveData.value
        spaceStationsLiveData.value?.forEachIndexed { index, spaceStationModel ->
            if (spaceStationModel.name == favoritesModel.name){
                tempList?.get(index)?.isFavorite = false
            }
        }
        spaceStationsLiveData.postValue(tempList)
        getAllFavorites()?.let {
            favoritesLiveData.postValue(it)
        }
    }

    fun pauseDamageTimer(){
        damageTimer?.cancel()
        damageTimer = null
        damageTimerIsPaused = true
    }

    fun continueDamageTimer(){
        if (damageTimerIsPaused){
            damageTimerIsPaused = false
            damageTimer?.cancel()
            damageTimer = null
            startDamageTimer(damageTimerMillisUntilFinished)
        }
    }

    private fun startDamageTimer(millisInFuture: Long){
        damageTimer = object : CountDownTimer(millisInFuture, ONE_SECOND_MILLIS){
            override fun onTick(millisUntilFinished: Long) {
                damageTimerMillisUntilFinished = millisUntilFinished
                damageTimeLiveData.postValue((millisUntilFinished / ONE_SECOND_MILLIS).toInt())
            }

            override fun onFinish() {
                damageTimeLiveData.postValue(0)
                damageCapacity.set(damageCapacity.get().orZero() - 10)
                if (damageCapacity.get().orZero() == 0){
                    setTravelEnd(spaceStationsLiveData.value,TravelEndReason.DAMAGE_OVER)
                }
                else {
                    damageTimeLiveData.postValue(dsValue.get().orZero()/1000)
                    damageTimer?.start()
                }
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        damageTimer?.cancel()
        damageTimer = null
        searchTimer?.cancel()
        searchTimer = null
    }

    companion object {
        private const val UGS_VARIABLE = 10000
        private const val EUS_VARIABLE = 200
        private const val DS_VARIABLE = 10000
        private const val DAMAGE_TIME_COUNT = 10
        private const val ONE_SECOND_MILLIS = 1000L
        private const val SEARCH_DELAY = 750L
        private const val WORLD = "Dünya"
    }

}