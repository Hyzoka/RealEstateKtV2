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

                var pictureEstateOne = PictureEstate(1,"living room","https://s.pro-immobilier.leboncoin.fr/api/v1/prd-media/images/ACCPI695979/43493025/2724437a-3bac-4336-8832-954db75b484f?rule=resize-to-jpeg")
                var pictureEstateOne1 = PictureEstate(2,"Kitchen","https://s.pro-immobilier.leboncoin.fr/api/v1/prd-media/images/ACCPI695979/43493025/2724437a-3bac-4336-8832-954db75b484f?rule=resize-to-jpeg")
                val pictureEstateListOne = arrayListOf(pictureEstateOne,pictureEstateOne1)



                val movieOne = Estate(1,"House","119","5","2","1","House renovated in 2016 of 119 m2, bright and quiet with garden and terrace, near the center and all amenities with quick access to public transport.\n" +
                        "It is composed of a fitted and equipped kitchen opening onto a large living room of more than 50 m2, a bedroom, a shower room, a laundry room and a toilet.\n" +
                        "On the first floor, you will find a landing that can be used as an office and a large bedroom with bathtub.\n" +
                        "Possibility to make a third bedroom of several means.","https://s.pro-immobilier.leboncoin.fr/api/v1/prd-media/images/ACCPI695979/43493025/2c00c539-33a5-4f7e-98fc-e358d57dd763?rule=resize-to-jpeg","29 rue Voltaire, 59234 Monchecourt, France","School, store","On sale","20/08/2021","not yep","Mme Ravière","170000")





                val movieTwo = Estate(2,"House","105","9","4","2","Nice detached house with a nice living room of more than 30m² open on a fitted kitchen, laundry room, office and a bedroom downstairs. Upstairs you will find 2 additional bedrooms and a bathroom.\n" +
                        "Outside there is a nice enclosed garden and a garage.\n" +
                        "Gas central heating, double glazing, mains drainage and private parking complete this charming house.\n" +
                        "Do not hesitate to contact us !","https://img.leboncoin.fr/api/v1/lbcpb1/images/3c/a3/43/3ca343d1e01cd52c1afbdfae9f49be931cac050f.jpg?rule=ad-large","41 Rue Waldeck Rousseau, 59234 Monchecourt, France","School","sold","19/05/2019","24/08/2021","Mm Ferrau","159000")






                val movieThree = Estate(3,"Villa","233","7","4","2","Beautiful detached house\n" +
                        "Come and discover in Monchecourt a semi detached house on one level, 5 bedrooms\n" +
                        "You will find there:\n" +
                        "-On the ground floor: a living room, a dining room, an equipped kitchen, an independent toilet, a bathroom, a games room of more than 50m ² and a bedroom.\n" +
                        "-On the 1st floor: a landing which serves 4 bedrooms\n" +
                        "-On the 2nd floor: attic space for conversion\n" +
                        "You will also enjoy a terrace and a large garden of 1000m², as well as a double garage.","https://img.leboncoin.fr/api/v1/lbcpb1/images/42/0b/ca/420bcad8fcbc6cdffff7ffd1f0976ad6fee2b985.jpg?rule=ad-large","6 Rue Pierre Bochu, 59234 Monchecourt, France","Work","on sale","11/08/2021","not yep","Mm Valiant","343000")


                val movieFour = Estate(4,"Maison","105","6","2","1","Only at Square Habitat Aniche, superb semi detached house completely renovated. It consists of a superb living room, an open kitchen fully equipped, bathroom with shower and bath, toilet. On the first floor, you will discover two beautiful bedrooms and attic suitable for conversion into third bedrooms. To complete this superb service, a cellar, a superb terrace giving on a pretty garden, privative parking and possibility of garage. " +
                        "","https://img.leboncoin.fr/api/v1/lbcpb1/images/43/43/2f/43432f37b2c792ee4350f54bb712a67c05a939cc.jpg?rule=ad-large","1 Rue René Silvain, 59234 Monchecourt, France","store, Work","sold","20/08/2021","29/08/2021","Mme Damiane","136900")
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