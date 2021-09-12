package com.ocr.realestatektv2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Estate::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("estateId"),
        onDelete = ForeignKey.CASCADE)))
data class PictureEstate(@PrimaryKey(autoGenerate = true)
                    var idPicture: Int = 0,
                         var estateId : Int = 0,
                    @ColumnInfo(name = "desc")
                    var pictureDescription: String,
                    @ColumnInfo(name = "url")
                    var url : String)