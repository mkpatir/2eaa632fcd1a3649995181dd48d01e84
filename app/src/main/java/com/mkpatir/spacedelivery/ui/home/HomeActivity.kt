package com.mkpatir.spacedelivery.ui.home

import android.content.Context
import android.content.Intent
import androidx.core.widget.doOnTextChanged
import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.databinding.ActivityHomeBinding
import com.mkpatir.spacedelivery.internal.extension.gone
import com.mkpatir.spacedelivery.internal.extension.visible
import com.mkpatir.spacedelivery.models.TravelEndReason
import com.mkpatir.spacedelivery.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: BaseActivity<ActivityHomeBinding,HomeViewModel>() {

    companion object {
        fun startHomeActivity(context: Context){
            context.startActivity(Intent(context,HomeActivity::class.java))
        }
    }

    private val stationAdapter = StationAdapter()
    private val favoritesAdapter = FavoritesAdapter()

    override fun setLayout(): Int = R.layout.activity_home

    override fun setViewModel(): Lazy<HomeViewModel> = viewModel()

    override fun setupUI() {
        getDataBinding().apply {
            stations.apply {
                homeViewModel = getViewModel()
                rvStations.adapter = stationAdapter
            }

            favorites.apply {
                rvFavorites.adapter = favoritesAdapter
            }
        }

        initListeners()
        initObservers()
    }

    private fun initListeners(){
        stationAdapter.onFavoriteClick = {
            getViewModel().addOrRemoveFavorite(it)
        }

        stationAdapter.onTravelClick = {
            getViewModel().travelToSpaceStation(it)
        }

        favoritesAdapter.removeFromFavoriteClick = {
            getViewModel().removeFromFavorites(it)
        }

        getDataBinding().stations.apply {
            editTextSearch.doOnTextChanged { _, _, _, _ ->
                if (editTextSearch.text.toString().length >= 3){
                    getViewModel().searchSpaceStation(editTextSearch.text.toString())
                }
            }
        }

        getDataBinding().bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_station -> {
                    getViewModel().continueDamageTimer()
                    getDataBinding().favorites.root.gone()
                    getDataBinding().stations.root.visible()
                }
                R.id.menu_favorites -> {
                    getViewModel().pauseDamageTimer()
                    getDataBinding().stations.root.gone()
                    getDataBinding().favorites.root.visible()
                }
            }
            true
        }
    }

    private fun initObservers(){
        getViewModel().apply {
            spaceStationsLiveData.observe(this@HomeActivity){ list ->
                stationAdapter.updateAdapter(list,getViewModel().currentLocationX,getViewModel().currentLocationY)
            }

            travelEndLiveData.observe(this@HomeActivity){
                showAlertDialog(
                    this@HomeActivity,
                    R.string.travel_finished_title,
                    getTravelEndReasonMessage(it),
                    R.string.ok
                )
            }

            damageTimeLiveData.observe(this@HomeActivity){
                getDataBinding().stations.valueDamageTime.text = getString(R.string.time,it)
            }

            rvIndexLiveData.observe(this@HomeActivity){
                getDataBinding().stations.rvStations.smoothScrollToPosition(it)
            }

            favoritesLiveData.observe(this@HomeActivity){
                favoritesAdapter.updateAdapter(it)
            }
        }
    }

    private fun getTravelEndReasonMessage(travelEndReason: TravelEndReason): Int{
        return when(travelEndReason){
            TravelEndReason.UGS_OVER -> R.string.ugs_over_message
            TravelEndReason.EUS_OVER -> R.string.eus_over_message
            TravelEndReason.INSUFFICIENT_EUS -> R.string.insufficient_eus_message
            TravelEndReason.DAMAGE_OVER -> R.string.damage_over_message
            TravelEndReason.FINISHED_ALL_TRAVEL -> R.string.finished_all_travel_message
        }
    }
}