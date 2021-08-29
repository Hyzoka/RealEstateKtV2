package com.ocr.realestatektv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ocr.realestatektv2.database.dao.EstateDao
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.util.Utils.subscribeOnBackground

@Database(
    entities = [Estate::class],
    version = 1,
    exportSchema = false
)
abstract class EstateRoomDatabase : RoomDatabase() {

    abstract fun estateDao(): EstateDao

    companion object {

        @Volatile
        private var INSTANCE: EstateRoomDatabase? = null

        fun getInstance(context: Context): EstateRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EstateRoomDatabase::class.java, "EstateRoomDatabase.db")
                .build()
    }
}
