package com.ocr.realestatektv2

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ocr.realestatektv2.database.ItemContentProvider
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyContentProviderTest {

    private lateinit var contentResolver: ContentResolver

    // DATA SET FOR TEST
    private val propertyID = 1
    private val nonExistingPropertyId = -1


    @Before
    fun setUp() {
        //CONTENT RESOLVER
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun whenFetchingNonExistingPropertyThenCursorIsEmpty() {
        //WHEN
        val cursor = contentResolver.query(ContentUris.withAppendedId(ItemContentProvider.URI_PROPERTY, nonExistingPropertyId.toLong()), null, null, null, null)
        //THEN
        Assert.assertNotNull(cursor)
        Assert.assertTrue(cursor!!.count == 0)
        cursor.close()
    }

    @Test
    fun getFirstPropertyOfTableIfExists() {
        //WHEN
        val cursor = contentResolver.query(ContentUris.withAppendedId(ItemContentProvider.URI_PROPERTY, propertyID.toLong()), null, null, null, null)
        //THEN
        if (cursor!!.count == 1) {
            //Make sure we're not running on an empty db
            Assert.assertTrue(cursor.moveToFirst())
            Assert.assertEquals(propertyID, cursor.getInt(cursor.getColumnIndexOrThrow("id")))
        }
        cursor.close()
    }

}