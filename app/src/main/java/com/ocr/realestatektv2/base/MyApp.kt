package com.ocr.realestatektv2.base

import android.app.Application
import com.ocr.realestatektv2.base.appmodule.appModule
import com.ocr.realestatektv2.base.appmodule.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun context(): MyApp {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(arrayListOf(appModule, applicationModule))
        }
    }
}