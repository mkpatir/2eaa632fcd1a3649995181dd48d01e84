package com.mkpatir.spacedelivery.ui.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mkpatir.spacedelivery.R

abstract class BaseActivity<D: ViewDataBinding,VM: BaseViewModel>: AppCompatActivity() {

    private lateinit var dataBinding: D
    private lateinit var viewModel: VM

    private var loadingFullScreen: LoadingFullScreen = LoadingFullScreen().apply {
        isCancelable = false
    }

    abstract fun setLayout(): Int

    abstract fun setViewModel(): Lazy<VM>

    abstract fun setupUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,setLayout())
        viewModel = setViewModel().value
        setupUI()
        initObservers()
    }

    fun getViewModel() = setViewModel().value

    fun getDataBinding() = dataBinding

    private fun initObservers(){
        getViewModel().apply {
            progressLiveData.observe(this@BaseActivity){
                if (it){
                    loadingFullScreen.show(supportFragmentManager, LOADING_TAG)
                }
                else {
                    loadingFullScreen.dismiss()
                }
            }
        }
    }

    fun showAlertDialog(
        context: Context,
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes positiveButtonText: Int,
        isNegativeButton: Boolean = false,
        positiveButtonClick: (() -> Unit)? = null
    ) {
        val dialog = MaterialAlertDialogBuilder(context)
        dialog.apply {
            setCancelable(false)
            setTitle(getString(title))
            setMessage(getString(message))
            setPositiveButton(
                positiveButtonText
            ) { dialog: DialogInterface, _: Int ->
                positiveButtonClick?.let { it() }
                dialog.dismiss()
            }
            if (isNegativeButton){
                setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }
            }
        }.show()
    }

    companion object {
        private const val LOADING_TAG = "Loading"
    }

}