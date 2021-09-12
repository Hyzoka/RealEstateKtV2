package com.ocr.realestatektv2.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomMasterTable.TABLE_NAME
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ocr.realestatektv2.database.dao.EstateDao
import com.ocr.realestatektv2.database.dao.PictureDao
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.util.Converter
import com.ocr.realestatektv2.util.DB_NAME
import com.ocr.realestatektv2.util.Utils
import com.ocr.realestatektv2.util.Utils.rePopulateDb
import com.ocr.realestatektv2.util.Utils.subscribeOnBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Estate::class, PictureEstate::class), version = 1, exportSchema = false)
abstract class EstateRoomDatabase : RoomDatabase() {

    abstract fun estateDao(): EstateDao
    abstract fun pictureDao(): PictureDao

    companion object {

        @Volatile
        private var INSTANCE: EstateRoomDatabase? = null

        fun getDatabase(context: Context): EstateRoomDatabase {
            if (INSTANCE == null) {
                synchronized(EstateRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            EstateRoomDatabase::class.java,
                            DB_NAME
                        )
                            //.allowMainThreadQueries() // Uncomment if you don't want to use RxJava or coroutines just yet (blocks UI thread)
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    Log.d("MoviesDatabase", "populating with data...")
                                    GlobalScope.launch(Dispatchers.IO) { rePopulateDb(INSTANCE) }
                                }
                            }).allowMainThreadQueries().build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}
