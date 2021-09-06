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
                val pictureEstate = PictureEstate(0,"living room","https://img.leboncoin.fr/api/v1/lbcpb1/images/00/1a/73/001a73d2bb314078f124d4186280153fdd7740e8.jpg?rule=ad-large")
                val pictureEstateOne = PictureEstate(1,"Kitchen","https://img.leboncoin.fr/api/v1/lbcpb1/images/96/fc/1c/96fc1ce05bc13f3b70b77e61939e911e7dce985f.jpg?rule=ad-large")
                val pictureEstateOne1 = PictureEstate(2,"Garden","https://img.leboncoin.fr/api/v1/lbcpb1/images/82/92/02/82920295ec22b39d27999f5e56d51e41a6210044.jpg?rule=ad-large")
                val pictureEstateListOne = arrayListOf(pictureEstate,pictureEstateOne,pictureEstateOne1)
                val pictureEstatetow = PictureEstate(0,"living room","https://img.leboncoin.fr/api/v1/lbcpb1/images/60/7e/1b/607e1b4fbe0b2ecd3703dfec079efd6db932102d.jpg?rule=ad-large")
                val pictureEstateTow = PictureEstate(1,"Kitchen","https://img.leboncoin.fr/api/v1/lbcpb1/images/03/e7/df/03e7df3af44ca6e8107f75762ce7c0b2533cef5a.jpg?rule=ad-large")
                val pictureEstateTow1 = PictureEstate(2,"verenda","https://img.leboncoin.fr/api/v1/lbcpb1/images/e2/73/ce/e273ce799a12d3602fd38f824f01b0d915db9d08.jpg?rule=ad-large")
                val pictureEstateTow2 = PictureEstate(3,"bathroom","https://img.leboncoin.fr/api/v1/lbcpb1/images/fd/a8/cd/fda8cd1c1391a37717ca5d8a24be70d0cc295f09.jpg?rule=ad-large")
                val pictureEstateListTow = arrayListOf(pictureEstatetow,pictureEstateTow,pictureEstateTow1,pictureEstateTow2)
                val pictureEstatetthree = PictureEstate(0,"living room","https://img.leboncoin.fr/api/v1/lbcpb1/images/7c/8c/53/7c8c53f9263090066bb9f53a582eb3cee4bbc4f1.jpg?rule=ad-large")
                val pictureEstateThree = PictureEstate(1,"Kitchen","https://img.leboncoin.fr/api/v1/lbcpb1/images/e9/11/bf/e911bf47d9220ce67958fafd31cddd38f11e6483.jpg?rule=ad-large")
                val pictureEstateThree1 = PictureEstate(2,"Garden","https://img.leboncoin.fr/api/v1/lbcpb1/images/fa/c2/6e/fac26e3a90daed5494a2db707423182b17088f5c.jpg?rule=ad-large")
                val pictureEstateThree2 = PictureEstate(3,"Garden","https://img.leboncoin.fr/api/v1/lbcpb1/images/0f/be/bb/0fbebb68d1e7021d330579dd2d7d25fff1a10689.jpg?rule=ad-large")
                val pictureEstateListThree = arrayListOf(pictureEstatetthree,pictureEstateThree,pictureEstateThree1,pictureEstateThree2)
                val pictureEstateFour = PictureEstate(0,"living room","https://img.leboncoin.fr/api/v1/lbcpb1/images/b1/75/62/b17562444add4f86bfec037ae99b41cf89e33387.jpg?rule=ad-large")
                val pictureEstateFour1 = PictureEstate(1,"Room","https://img.leboncoin.fr/api/v1/lbcpb1/images/39/3b/f9/393bf9f8050848fd5e9613908567cbccd5db5333.jpg?rule=ad-large")
                val pictureEstateFour0 = PictureEstate(2,"room","https://img.leboncoin.fr/api/v1/lbcpb1/images/b9/7e/c4/b97ec43eed5cad8724c736db20226bdeb09ab285.jpg?rule=ad-large")
                val pictureEstateFour2 = PictureEstate(3,"Kitchen","https://img.leboncoin.fr/api/v1/lbcpb1/images/10/1f/e4/101fe4ad9d7d6ad5ca46fe12958f582a8d3a763b.jpg?rule=ad-large")
                val pictureEstateFour3 = PictureEstate(4,"bathroom","https://img.leboncoin.fr/api/v1/lbcpb1/images/4e/7f/8f/4e7f8fc57c96a6fc26a689c8865101bb42d83898.jpg?rule=ad-large")
                val pictureEstateFour4 = PictureEstate(5,"Garden","https://img.leboncoin.fr/api/v1/lbcpb1/images/7e/69/fb/7e69fbdd94a21abfbb72e55684e40d6f6c655e5c.jpg?rule=ad-large")
                val pictureEstateFour5 = PictureEstate(6,"garage","https://img.leboncoin.fr/api/v1/lbcpb1/images/10/d9/dd/10d9dd6b28278633fbed50e39030967379e57f21.jpg?rule=ad-large")
                val pictureEstateListFour = arrayListOf(pictureEstateFour,pictureEstateFour1,pictureEstateFour0,pictureEstateFour2,pictureEstateFour3,pictureEstateFour4,pictureEstateFour5)

                val movieOne = Estate(1,"House","119","5","2","1","House renovated in 2016 of 119 m2, bright and quiet with garden and terrace, near the center and all amenities with quick access to public transport.\n" +
                        "It is composed of a fitted and equipped kitchen opening onto a large living room of more than 50 m2, a bedroom, a shower room, a laundry room and a toilet.\n" +
                        "On the first floor, you will find a landing that can be used as an office and a large bedroom with bathtub.\n" +
                        "Possibility to make a third bedroom of several means.",pictureEstateListOne,"29 rue Voltaire, 59234 Monchecourt, France","School, store","On sale","20/08/2021","not yep","Mme Ravière","170000")

                val movieTwo = Estate(2,"House","105","9","4","2","Nice detached house with a nice living room of more than 30m² open on a fitted kitchen, laundry room, office and a bedroom downstairs. Upstairs you will find 2 additional bedrooms and a bathroom.\n" +
                        "Outside there is a nice enclosed garden and a garage.\n" +
                        "Gas central heating, double glazing, mains drainage and private parking complete this charming house.\n" +
                        "Do not hesitate to contact us !",pictureEstateListTow,"41 Rue Waldeck Rousseau, 59234 Monchecourt, France","School","sold","19/05/2019","24/08/2021","Mm Ferrau","159000")

                val movieThree = Estate(3,"Villa","233","7","4","2","Beautiful detached house\n" +
                        "Come and discover in Monchecourt a semi detached house on one level, 5 bedrooms\n" +
                        "You will find there:\n" +
                        "-On the ground floor: a living room, a dining room, an equipped kitchen, an independent toilet, a bathroom, a games room of more than 50m ² and a bedroom.\n" +
                        "-On the 1st floor: a landing which serves 4 bedrooms\n" +
                        "-On the 2nd floor: attic space for conversion\n" +
                        "You will also enjoy a terrace and a large garden of 1000m², as well as a double garage.",pictureEstateListThree,"6 Rue Pierre Bochu, 59234 Monchecourt, France","Work","on sale","11/08/2021","not yep","Mm Valiant","343000")


                val movieFour = Estate(4,"Maison","105","6","2","1","Only at Square Habitat Aniche, superb semi detached house completely renovated. It consists of a superb living room, an open kitchen fully equipped, bathroom with shower and bath, toilet. On the first floor, you will discover two beautiful bedrooms and attic suitable for conversion into third bedrooms. To complete this superb service, a cellar, a superb terrace giving on a pretty garden, privative parking and possibility of garage. " +
                        "",pictureEstateListFour,"1 Rue René Silvain, 59234 Monchecourt, France","store, Work","sold","20/08/2021","29/08/2021","Mme Damiane","136900")
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