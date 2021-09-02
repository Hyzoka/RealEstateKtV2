package com.ocr.realestatektv2.base.appmodule

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.ocr.realestatektv2.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {

    single { getSharedPreferences(androidContext().applicationContext) }
    single { getResources(get()) }
    single { getPackageInfo(get()) }
}

fun getPackageInfo(context: Context): Any {
    return context.packageManager.getPackageInfo(context.packageName, 0)
}

private fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
}

private fun getResources(context: Context): Resources {
    return context.resources
}
