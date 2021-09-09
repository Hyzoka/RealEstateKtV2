package com.ocr.realestatektv2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "pictureEstate")
data class PictureEstate(@PrimaryKey(autoGenerate = true)
                    var idPicture: Int = 0,
                    @ColumnInfo(name = "desc")
                    var pictureDescription: String,
                    @ColumnInfo(name = "url")
                    var url : String)