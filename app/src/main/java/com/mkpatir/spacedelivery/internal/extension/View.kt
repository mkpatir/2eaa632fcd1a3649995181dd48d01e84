package com.mkpatir.spacedelivery.internal.extension

import android.animation.Animator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.widget.SeekBar
import com.airbnb.lottie.LottieAnimationView

fun SeekBar.onProgressChanged(onProgressChanged:(progress: Int) -> Unit){
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser){
                onProgressChanged(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    })
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun ViewPropertyAnimator.onAnimationEnd(onAnimationEnd:() -> Unit){
    setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd()
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }

    })
}

fun LottieAnimationView.onAnimationEnd(onAnimationEnd: () -> Unit){
    addAnimatorListener(object : Animator.AnimatorListener{
        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd()
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }

    })
}