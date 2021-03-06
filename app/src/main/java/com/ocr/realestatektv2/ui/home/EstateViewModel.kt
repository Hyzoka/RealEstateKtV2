package com.ocr.realestatektv2.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.ocr.realestatektv2.database.EstateRoomDatabase
import com.ocr.realestatektv2.database.dao.EstateDao
import com.ocr.realestatektv2.database.dao.PictureDao
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.PictureEstate


class EstateViewModel(application: Application) : AndroidViewModel(application) {

    private val estateDao: EstateDao = EstateRoomDatabase.getDatabase(application).estateDao()
    private val pictureDao: PictureDao = EstateRoomDatabase.getDatabase(application).pictureDao()

    val estateList: LiveData<List<Estate>> = estateDao.getAllEstate()

    fun getEstateById(id: Int) : Estate{
        return  estateDao.getEstateById(id)
    }
    fun getPictureEstate(id: Int) : LiveData<List<PictureEstate>>{
         return pictureDao.getPicture(id)
    }

    fun getFilterList(sqlRequest: String): List<Estate>?{
        val query = SimpleSQLiteQuery(sqlRequest)
        return  estateDao.getFilterEstate(query)

    }

    fun getIdMaxPicture() : Int{
        return  pictureDao.getPicture()
    }

    fun insertPicture(vararg estatePicture: PictureEstate) {
        pictureDao.insert(*estatePicture)
    }
     fun insert(vararg estate: Estate) {
        estateDao.insert(*estate)
    }

     fun update(estate: Estate) {
        estateDao.update(estate)
    }

    fun updatePicture(estate: PictureEstate) {
        pictureDao.updateItem(estate)
    }

    suspend fun deleteAll() {
        estateDao.deleteAll()
    }
}