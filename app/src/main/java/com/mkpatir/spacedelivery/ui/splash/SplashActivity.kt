package com.mkpatir.spacedelivery.ui.splash

import android.view.View
import android.util.Pair
import androidx.appcompat.widget.AppCompatTextView
import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.databinding.ActivitySplashBinding
import com.mkpatir.spacedelivery.internal.extension.onAnimationEnd
import com.mkpatir.spacedelivery.ui.base.BaseActivity
import com.mkpatir.spacedelivery.ui.base.EmptyViewModel
import com.mkpatir.spacedelivery.ui.spaceship.SpaceShipActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity<ActivitySplashBinding,EmptyViewModel>() {

    companion object {
        private const val ANIMATION_TIME = 759L
    }

    override fun setLayout(): Int = R.layout.activity_splash

    override fun setViewModel(): Lazy<EmptyViewModel> = viewModel()

    override fun setupUI() {
        getDataBinding().apply {

            lottieView.animate().apply {
                alpha(1f)
                translationY(0f)
                duration = ANIMATION_TIME
                onAnimationEnd {
                    setAnimationToTextViews(listOf(appNameFirstPart,appNameSecondPart))
                    lottieView.playAnimation()
                    lottieView.onAnimationEnd {
                        val lottieTransition = Pair<View,String>(lottieView,getString(R.string.transition_lottie))
                        SpaceShipActivity.startSpaceShipActivity(this@SplashActivity,lottieTransition)
                    }
                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun setAnimationToTextViews(list: List<AppCompatTextView>){
        list.forEach {
            it.animate().apply {
                alpha(1f)
                translationX(0f)
                duration = ANIMATION_TIME
            }
        }
    }
}