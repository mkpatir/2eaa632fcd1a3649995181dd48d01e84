package com.mkpatir.spacedelivery.ui.splash

import android.animation.Animator
import com.mkpatir.spacedelivery.R
import com.mkpatir.spacedelivery.databinding.ActivitySplashBinding
import com.mkpatir.spacedelivery.ui.base.BaseActivity
import com.mkpatir.spacedelivery.ui.base.EmptyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity<ActivitySplashBinding,EmptyViewModel>() {

    companion object {
        private const val ANIMATION_TIME = 759L
    }

    override fun setLayout(): Int = R.layout.activity_splash

    override fun setViewModel(): Lazy<EmptyViewModel> = viewModel()

    override fun setupUI() {
        getDataBinding().apply {

            /*lottieView.animate().apply {
                alpha(1f)
                translationY(0f)
                duration = ANIMATION_TIME
                setListener(getAnimationListener(AnimationType.IMAGE_TRANSITION))
            }*/
        }
    }

    private fun getAnimationListener(type: AnimationType) = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            when(type){
                AnimationType.IMAGE_TRANSITION -> {
                    getDataBinding().appNameFirstPart.animate().apply {
                        alpha(1f)
                        translationX(0f)
                        duration = ANIMATION_TIME
                    }
                }
                AnimationType.LOTTIE -> {

                }
                AnimationType.TEXT -> {

                }
            }
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }

    }

    enum class AnimationType{
        IMAGE_TRANSITION,
        LOTTIE,
        TEXT
    }
}