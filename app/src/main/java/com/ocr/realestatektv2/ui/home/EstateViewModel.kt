package com.ocr.realestatektv2.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ocr.realestatektv2.database.EstateRoomDatabase
import com.ocr.realestatektv2.database.dao.EstateDao
import com.ocr.realestatektv2.model.Estate

class EstateViewModel (application: Application) : AndroidViewModel(application) {

    private val estateDao: EstateDao = EstateRoomDatabase.getDatabase(application).estateDao()

    val estateList: LiveData<List<Estate>> = estateDao.getAllEstate()

    fun getEstateById(id : Int) : Estate{
        return  estateDao.getEstateById(id)
    }
     fun insert(vararg estate: Estate) {
        estateDao.insert(*estate)
    }

     fun update(estate: Estate) {
        estateDao.update(estate)
    }

    suspend fun deleteAll() {
        estateDao.deleteAll()
    }
}