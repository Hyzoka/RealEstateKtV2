package com.ocr.realestatektv2.util

import android.app.Instrumentation
import android.content.Context
import androidx.test.InstrumentationRegistry
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UtilsTest : TestCase() {

    fun testConvertDollarToEuro() {
        Assert.assertEquals(0.812, Utils.convertDollarToEuro(1),0.001)
    }

    fun testConvertEuroToDollar() {
//        Assert.assertEquals(1.18, Utils.convertEuroToDollar(1))
        Assert.assertEquals(1.18, Utils.convertEuroToDollar(1),0.001)
    }

    fun testTodayDateFormatFR() {
        Assert.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FR_FORMAT)),Utils.todayDateFormatFR())
    }

    fun testTodayDateFormatUS() {
        Assert.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_US_FORMAT)),Utils.todayDateFormatUS())
    }

    fun testAddTaux() {
        //take 0.82% on 100k
        Assert.assertEquals(50,Utils.addTaux(100,50.0))
    }
    fun testConvertYearToMonth() {
        Assert.assertEquals(12,Utils.convertYearToMonth(1))
    }
    fun testLoanSimulator() {
        Assert.assertEquals(1567,Utils.loanSimulator(280000,15,0.79,0))
    }
}