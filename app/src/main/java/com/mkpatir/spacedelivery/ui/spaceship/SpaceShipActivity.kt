package com.mkpatir.spacedelivery.ui.spaceship

import android.content.Intent
import androidx.lifecycle.Observer
import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.databinding.ActivitySpaceshipBinding
import com.mkpatir.spacedelivery.internal.extension.onProgressChanged
import com.mkpatir.spacedelivery.models.CheckProperty
import com.mkpatir.spacedelivery.models.SpaceShipProperty
import com.mkpatir.spacedelivery.ui.base.BaseActivity
import com.mkpatir.spacedelivery.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpaceShipActivity: BaseActivity<ActivitySpaceshipBinding,SpaceShipViewModel>() {

    override fun setLayout(): Int = R.layout.activity_spaceship

    override fun setViewModel(): Lazy<SpaceShipViewModel> = viewModel()

    override fun setupUI() {
        getDataBinding().spaceShipViewModel = getViewModel()
        initListeners()
        initObservers()
    }

    private fun initListeners(){
        getDataBinding().apply {
            seekBarDurability.onProgressChanged { progress ->
                getViewModel().updateProperties(progress,SpaceShipProperty.DURABILITY)
            }
            seekBarSpeed.onProgressChanged { progress ->
                getViewModel().updateProperties(progress,SpaceShipProperty.SPEED)
            }
            seekBarCapacity.onProgressChanged { progress ->
                getViewModel().updateProperties(progress,SpaceShipProperty.CAPACITY)
            }
            buttonContinue.setOnClickListener {
                getViewModel().checkProperties(spaceshipName.text.toString())
            }
        }
    }

    private fun initObservers(){
        getViewModel().apply {
            checkPropertyLiveData.observe(this@SpaceShipActivity, { it ->
                it?.let { property ->
                    when(property){
                        CheckProperty.CONTINUE -> {
                            startActivity(Intent(this@SpaceShipActivity,HomeActivity::class.java))
                        }
                        CheckProperty.CONTINUE_WITH_MISSING_COUNT -> {}
                        CheckProperty.MISSING_PROPERTY -> {}
                    }
                }
            })
        }
    }

}