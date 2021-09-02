package com.ocr.realestatektv2.model

import androidx.room.*
import com.ocr.realestatektv2.util.Converter
import com.ocr.realestatektv2.util.Utils

@Entity(tableName = "estate")
class Estate(@PrimaryKey(autoGenerate = true)
             var id: Int = 0,

             @ColumnInfo(name = "type") var typeEstate: String,
             @ColumnInfo(name = "surface") val surface: String,
             @ColumnInfo(name = "number_of_room") val nbrRoom: String,
             @ColumnInfo(name = "number_of_bed_room") val nbrBedRoom: String,
             @ColumnInfo(name = "number_of__bath_room") val nbrBathRoom: String,
             @ColumnInfo(name = "description") val description: String,
             @ColumnInfo(name = "pictureList")
         //   @TypeConverters(Converter::class)
             //val picture: ArrayList<PictureEstate>,
             val picture: String,
             @ColumnInfo(name = "address") val addresse: String,
             @ColumnInfo(name = "proximity_address") val proxyAddress: String,
             @ColumnInfo(name = "status") val status: String,
             @ColumnInfo(name = "date_create") val createDate: String,
             @ColumnInfo(name = "date_sell") val sellDate: String,
             @ColumnInfo(name = "manager") val manager: String,
             @ColumnInfo(name = "price") val price: String)