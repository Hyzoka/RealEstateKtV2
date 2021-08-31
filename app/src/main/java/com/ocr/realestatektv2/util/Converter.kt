package com.ocr.realestatektv2.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ocr.realestatektv2.model.PictureEstate

class Converter {

    @TypeConverter
    fun listToJson(value: ArrayList<PictureEstate>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<PictureEstate>::class.java).toList()
}