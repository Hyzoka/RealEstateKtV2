package com.ocr.realestatektv2.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estate")
class Estate(@PrimaryKey(autoGenerate = true)
             var id: Int = 0 ,// or foodId: Int? = null

             @ColumnInfo(name = "type") val typeEstate: String,
             @ColumnInfo(name = "surface") val surface: String,
             @ColumnInfo(name = "number_of_room") val nbrRoom: String,
             @ColumnInfo(name = "description") val description: String,
             @Embedded val picture: PictureEstate?,
             @ColumnInfo(name = "address") val addresse: String,
             @ColumnInfo(name = "proximity_address") val proxyAddress: String,
             @ColumnInfo(name = "status") val status: String,
             @ColumnInfo(name = "date_create") val createDate: String,
             @ColumnInfo(name = "date_sell") val sellDate: String,
             @ColumnInfo(name = "manager") val manager: String,
              @ColumnInfo(name = "price") val price: String)