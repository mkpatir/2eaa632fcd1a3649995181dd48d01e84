package com.mkpatir.spacedelivery.ui.home

import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.databinding.ActivityHomeBinding
import com.mkpatir.spacedelivery.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: BaseActivity<ActivityHomeBinding,HomeViewModel>() {

    private val stationAdapter = StationAdapter()

    override fun setLayout(): Int = R.layout.activity_home

    override fun setViewModel(): Lazy<HomeViewModel> = viewModel()

    override fun setupUI() {
        getDataBinding().apply {
            homeViewModel = getViewModel()
            rvStations.adapter = stationAdapter
        }

        initListeners()
        initObservers()
    }

    private fun initListeners(){
        stationAdapter.onFavoriteClick = {

        }
    }

    private fun initObservers(){
        getViewModel().apply {
            spaceStationsLiveData.observe(this@HomeActivity){
                stationAdapter.updateAdapter(it)
            }
        }
    }
}