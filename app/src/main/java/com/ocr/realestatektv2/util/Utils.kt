package com.ocr.realestatektv2.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun convertDollarToEuro(dollars: Int): Int {
        return Math.round(dollars * 0.812).toInt()
    }

    fun convertEuroToDollar(dollars: Int): Int {
        return Math.round(dollars * 0.812).toInt()
    }

    fun todayDateFormatFR() : String{
        val dateFormat: DateFormat = SimpleDateFormat(DATE_FR_FORMAT)
        return dateFormat.format(Date())
    }

    fun todayDateFormatUS() : String{
        val dateFormat: DateFormat = SimpleDateFormat(DATE_US_FORMAT)
        return dateFormat.format(Date())
    }

    fun isInternetAvailable(context: Context): Boolean {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.isWifiEnabled
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var result = false
        if (activeNetwork != null) {
            result = activeNetwork.isConnectedOrConnecting
        }
        return result
    }

    fun addTaux(target: Int,taux : Double): Int{
        return Math.round(target*taux/100).toInt()
    }

    fun  convertYearToMonth(year : Int): Int{
        return Math.round((year * 12).toDouble()).toInt()
    }

    fun loanSimulator(montant : Int,duree : Int, taux : Double, apport : Int) : Int{
        val total = montant - apport
        Log.i("TOTAL", total.toString())
        val totalWithTaux = total + addTaux(total,taux)
        Log.i("TOTALWithTaux", totalWithTaux.toString())
        val converMonth = convertYearToMonth(15)
        Log.i("CONVERTMONTH", converMonth.toString())
        val perMonth = totalWithTaux/convertYearToMonth(duree)
        Log.i("PERMONTH", perMonth.toString())

        return  totalWithTaux/convertYearToMonth(duree)

    }

    fun subscribeOnBackground(function: () -> Unit) {
        Single.fromCallable {
            function()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}