package com.ocr.realestatektv2.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import com.ocr.realestatektv2.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ocr.realestatektv2.ui.home.EstateViewModel
import org.jetbrains.anko.contentView
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

abstract class EstateBaseActivity<T : BaseViewModel>: BaseActivity() {

    internal lateinit var viewModel: T
    abstract fun viewModel() : T
    internal var activityToStart = true

    abstract fun setupViewModel()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = viewModel()

        if (activityToStart)
            viewModel.activityToStart.observe(this, Observer { value ->
                val intent = Intent(this, value.first.java)
                if (value.second != null)
                    intent.putExtras(value.second!!)
                if (value.third != null)
                    intent.flags = value.third!!
                startActivity(intent)
            })
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (findViewById<Guideline>(R.id.marginTop) != null) {
                val statusBarHeight: Int
                val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    statusBarHeight = resources.getDimensionPixelSize(resourceId)
                    val guideL = findViewById<Guideline>(R.id.marginTop)
                    val constParam: ConstraintLayout.LayoutParams =
                        guideL.layoutParams as ConstraintLayout.LayoutParams
                    constParam.guideBegin = statusBarHeight
                    guideL.layoutParams = constParam
                }
            }

        setupViewModel()
    }

    protected fun showClassicToast(content: String){
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    protected fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.contentView?.windowToken, 0)
    }
}