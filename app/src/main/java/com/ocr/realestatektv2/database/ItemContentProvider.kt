package com.ocr.realestatektv2.database

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.ocr.realestatektv2.model.Estate


class ItemContentProvider  : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        val TABLE_NAME: String = Estate::class.java.simpleName
        val URI_PROPERTY: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalStateException("RealEstateManager is read only.")
    }


    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {

        context?.let {  val propertyId = ContentUris.parseId(uri)
            val cursor: Cursor? = EstateRoomDatabase.getDatabase(it).estateDao().getEstateWithCursor(propertyId)
            cursor?.setNotificationUri((it).contentResolver, uri)
            return cursor
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalStateException("RealEstateManager is read only.")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalStateException("RealEstateManager is read only.")
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

}