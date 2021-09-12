package com.ocr.realestatektv2.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ocr.realestatektv2.model.Estate


@Dao
interface EstateDao {

    @Query("SELECT * FROM estate")
    fun getAllEstate(): LiveData<List<Estate>>

    //For ContentProvider
    @Query("SELECT * FROM estate WHERE id = :estateId")
    fun getEstateWithCursor(estateId: Long): Cursor

    @RawQuery
    fun getFilterEstate(query: SupportSQLiteQuery?): List<Estate>?

//    @Query("SELECT SUM(pictureList) FROM estate;")
//    fun getNumberOfPicture() : LiveData<Int>

    @Query("SELECT * FROM estate WHERE id=:idEstate ")
    fun getEstateById(vararg idEstate: Int): Estate

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