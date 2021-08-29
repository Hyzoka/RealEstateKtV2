package com.ocr.realestatektv2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocr.realestatektv2.database.repo.EstateRepository

class ViewModelFactoryEstateList (private val skiResortRepo: EstateRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstateViewModel(skiResortRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}