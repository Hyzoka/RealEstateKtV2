package com.ocr.realestatektv2.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ocr.realestatektv2.database.EstateRoomDatabase
import com.ocr.realestatektv2.database.dao.EstateDao
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.PictureEstate
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun rePopulateDb(database: EstateRoomDatabase?) {
        database?.let { db ->
            withContext(Dispatchers.IO) {
                val movieDao: EstateDao = db.estateDao()

                movieDao.deleteAll()

                var pictureEstate = PictureEstate(1,"Facade","https://images.unsplash.com/photo-1599809275671-b5942cabc7a2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cmVhbCUyMGVzdGF0ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")
                val pictureEstateList = arrayListOf<PictureEstate>(pictureEstate)
                val movieOne = Estate(1,"Appart","50","3","1","1","blablablablalb","https://images.unsplash.com/photo-1599809275671-b5942cabc7a2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cmVhbCUyMGVzdGF0ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","3, boulevard Fontaine, RiouVille","ecole, commerce","Vendu","20/08/2021","29/08/2021","Mme Ravière","88000")
                val movieTwo = Estate(2,"Maison","123","5","2","1","blalbalbalblabla","https://images.unsplash.com/photo-1599809275671-b5942cabc7a2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cmVhbCUyMGVzdGF0ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","3, boulevard Fontaine, RiouVille","ecole, commerce","Vendu","20/08/2021","29/08/2021","Mme Ravière","88000")
                val movieThree = Estate(3,"Maison","168","7","4","2","blalbalblablalb","https://images.unsplash.com/photo-1599809275671-b5942cabc7a2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cmVhbCUyMGVzdGF0ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","3, boulevard Fontaine, RiouVille","ecole, commerce","Vendu","20/08/2021","29/08/2021","Mme Ravière","88000")
                val movieFour = Estate(4,"Villa","352","12","5","3","blalbalblabllb","https://images.unsplash.com/photo-1599809275671-b5942cabc7a2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cmVhbCUyMGVzdGF0ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","3, boulevard Fontaine, RiouVille","ecole, commerce","Vendu","20/08/2021","29/08/2021","Mme Ravière","88000")
                movieDao.insert(movieFour,movieOne,movieTwo,movieThree)
            }
        }
    }

    fun View.visible() { this.visibility = View.VISIBLE }
    fun View.invisible() { this.visibility = View.INVISIBLE }
    fun View.gone() { this.visibility = View.GONE }

    fun View.setMargins(scale: Float, left: Int, top: Int, right: Int, bottom: Int){
        val margins = layoutParams as ViewGroup.MarginLayoutParams
        margins.setMargins(left.dpToPx(scale), top.dpToPx(scale), right.dpToPx(scale), bottom.dpToPx(scale))
        margins.marginEnd = right.dpToPx(scale)
        margins.marginStart = left.dpToPx(scale)
        layoutParams = margins
    }

    fun Int.dpToPx(scale: Float): Int{ return (this * scale + 0.5f).toInt() }

    fun dpToPx(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    fun ImageView.load(
        url: Any?,
        options: RequestOptions = RequestOptions(),
        transition: DrawableTransitionOptions = DrawableTransitionOptions(),
        onLoadingFinished: () -> Unit = {}
    ) {
        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }
        }
        Glide.with(this)
            .load(url)
            .apply(options.diskCacheStrategy(DiskCacheStrategy.ALL))
            .listener(listener)
            .transition(transition)
            .into(this)
    }
}