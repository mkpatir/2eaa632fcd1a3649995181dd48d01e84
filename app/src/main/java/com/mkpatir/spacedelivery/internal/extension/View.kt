package com.mkpatir.spacedelivery.internal.extension

import android.widget.SeekBar

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