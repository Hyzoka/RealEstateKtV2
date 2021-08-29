package com.ocr.realestatektv2.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ocr.realestatektv2.database.EstateRoomDatabase
import com.ocr.realestatektv2.database.repo.EstateRepository
import com.ocr.realestatektv2.ui.home.ViewModelFactoryEstateList
import java.util.concurrent.Executors


object Injection{

    private fun provideSkiResortRepo(context: Context): EstateRepository {
        val database = EstateRoomDatabase.getInstance(context)
        return EstateRepository(SkiResortListService.create(), database.estateDao(), Executors.newSingleThreadExecutor())
    }

    fun provideViewModelFactorySkiResortList(context: Context): ViewModelProvider.Factory {
        return ViewModelFactoryEstateList(provideSkiResortRepo(context))
    }
}