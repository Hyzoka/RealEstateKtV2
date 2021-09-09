package com.ocr.realestatektv2

import android.content.Context
import android.net.wifi.WifiManager
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ocr.realestatektv2.database.EstateRoomDatabase
import com.ocr.realestatektv2.database.dao.EstateDao
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.util.Utils
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private var estateList: List<Estate>? = null
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var userDao: EstateDao
    private lateinit var db: EstateRoomDatabase
    @Before
        fun createDb() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = Room.inMemoryDatabaseBuilder(
                    context, EstateRoomDatabase::class.java).build()
            userDao = db.estateDao()
        estateList = db.estateDao().getAllEstate().value
        }

        @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ocr.realestatektv2", appContext.packageName)
    }

    @Test
    fun isConnected() {
        // Context of the app under test.
        assertEquals(true, Utils.isConnected(appContext))
    }


    @Test
    fun addEstate(){
        val pictureEstate = PictureEstate(0, "living room", "https://img.leboncoin.fr/api/v1/lbcpb1/images/00/1a/73/001a73d2bb314078f124d4186280153fdd7740e8.jpg?rule=ad-large")
        val pictureEstateOne = PictureEstate(1, "Kitchen", "https://img.leboncoin.fr/api/v1/lbcpb1/images/96/fc/1c/96fc1ce05bc13f3b70b77e61939e911e7dce985f.jpg?rule=ad-large")
        val pictureEstateOne1 = PictureEstate(2, "Garden", "https://img.leboncoin.fr/api/v1/lbcpb1/images/82/92/02/82920295ec22b39d27999f5e56d51e41a6210044.jpg?rule=ad-large")
        val pictureEstateListOne = arrayListOf(pictureEstate, pictureEstateOne, pictureEstateOne1)

        val estate = Estate(5, "House", "119", "5", "2", "1", "House renovated in 2016 of 119 m2, bright and quiet with garden and terrace, near the center and all amenities with quick access to public transport.\n" +
                "It is composed of a fitted and equipped kitchen opening onto a large living room of more than 50 m2, a bedroom, a shower room, a laundry room and a toilet.\n" +
                "On the first floor, you will find a landing that can be used as an office and a large bedroom with bathtub.\n" +
                "Possibility to make a third bedroom of several means.", pictureEstateListOne, "29 rue Voltaire, 59234 Monchecourt, France", "School, Store", "sell", "20/08/2021", "not yep", "Mme Ravière", "170000")

        db.estateDao().insert(estate)
    }

    @Test
    fun UpdateEstate(){
        val pictureEstate = PictureEstate(0, "living room", "https://img.leboncoin.fr/api/v1/lbcpb1/images/00/1a/73/001a73d2bb314078f124d4186280153fdd7740e8.jpg?rule=ad-large")
        val pictureEstateOne = PictureEstate(1, "Kitchen", "https://img.leboncoin.fr/api/v1/lbcpb1/images/96/fc/1c/96fc1ce05bc13f3b70b77e61939e911e7dce985f.jpg?rule=ad-large")
        val pictureEstateOne1 = PictureEstate(2, "Garden", "https://img.leboncoin.fr/api/v1/lbcpb1/images/82/92/02/82920295ec22b39d27999f5e56d51e41a6210044.jpg?rule=ad-large")
        val pictureEstateListOne = arrayListOf(pictureEstate, pictureEstateOne, pictureEstateOne1)

        val estate = Estate(5, "House", "119", "5", "2", "1", "House renovated in 2016 of 119 m2, bright and quiet with garden and terrace, near the center and all amenities with quick access to public transport.\n" +
                "It is composed of a fitted and equipped kitchen opening onto a large living room of more than 50 m2, a bedroom, a shower room, a laundry room and a toilet.\n" +
                "On the first floor, you will find a landing that can be used as an office and a large bedroom with bathtub.\n" +
                "Possibility to make a third bedroom of several means.", pictureEstateListOne, "29 rue Voltaire, 59234 Monchecourt, France", "School, Store", "sell", "20/08/2021", "not yep", "Mme Ravière", "170000")

        db.estateDao().update(estate)
    }

    @Test
    fun filterList(){
        val query = SimpleSQLiteQuery("SELECT * FROM estate WHERE surface >= 200 AND surface <= 300 AND proximity_address LIKE '%School%' AND  proximity_address LIKE '%Store%' AND date_create > 01-07-2021;")
         estateList = db.estateDao().getFilterEstate(query)
        assertEquals(0, estateList?.size)
    }
}