package com.ocr.realestatektv2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ocr.realestatektv2.database.repo.EstateRepository
import com.ocr.realestatektv2.model.Estate

class EstateViewModel (skiResortRepo: EstateRepository) : ViewModel() {

    //list of all the ski resorts
    val skiResortList : LiveData<List<Estate>> = skiResortRepo.getAllSkiResorts()
}