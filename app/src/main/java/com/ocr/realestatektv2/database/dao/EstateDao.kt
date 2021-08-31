package com.ocr.realestatektv2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ocr.realestatektv2.model.Estate

@Dao
interface EstateDao {

    @Query("SELECT * FROM estate")
    fun getAllEstate(): LiveData<List<Estate>>

    @Query("SELECT * FROM estate WHERE id IN (:estateId)")
    fun loadAllByIds(estateId: IntArray): LiveData<List<Estate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg directors: Estate)

    @Update
    fun update(vararg estate: Estate)

    @Delete
    fun deleteEstate(vararg estate: Estate)

    @Query("DELETE FROM estate")
    fun deleteAll()
}