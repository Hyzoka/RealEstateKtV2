package com.ocr.realestatektv2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.PictureEstate


@Dao
interface PictureDao {
    @Query("SELECT * FROM PictureEstate WHERE estateId = :estateId")
    fun getPicture(estateId: Int): LiveData<List<PictureEstate>>

    @Query("SELECT MAX(idPicture) FROM PictureEstate")
    fun getPicture(): Int

    @Insert
    fun insert(vararg directors: PictureEstate)

    @Update
    fun updateItem(vararg item: PictureEstate)
}