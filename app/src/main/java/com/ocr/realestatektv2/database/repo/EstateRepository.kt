package com.ocr.realestatektv2.database.repo

import androidx.lifecycle.LiveData
import com.ocr.realestatektv2.database.dao.EstateDao
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.util.SkiResortListService
import com.ocr.realestatektv2.util.requestSkiResort
import java.util.concurrent.Executor


class EstateRepository(private val skiResortListService: SkiResortListService, private val skiResortDao: EstateDao, private val ioExecutor: Executor) {

    fun getAllSkiResorts(): LiveData<List<Estate>> {
        requestSkiResort(skiResortListService, {
                skiResorts ->
            ioExecutor.execute {
                skiResortDao.insert(skiResorts)
            }
        }, { error ->

        })
        return skiResortDao.getAllEstate()
    }
}