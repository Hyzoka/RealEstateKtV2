package com.ocr.realestatektv2.ui.home.filter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.base.BaseActivity
import com.ocr.realestatektv2.util.*
import kotlinx.android.synthetic.main.filter_activity.*
import kotlinx.android.synthetic.main.filter_activity.proxyParc
import kotlinx.android.synthetic.main.filter_activity.proxySchool
import kotlinx.android.synthetic.main.filter_activity.proxyStore
import kotlinx.android.synthetic.main.filter_activity.proxyWork
import org.jetbrains.anko.notificationManager

class FilterActivity : BaseActivity(){

    val sqlRequest = StringBuilder()

    var listFilterShow = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_activity)
        filterButton.isActive = true
        DateInputMask(publishEdit).listen()
        DateInputMask(inputDateSold).listen()
        setupListener()
    }

    private fun setupListener(){
        back.setOnClickListener {
            finish()
        }

        skipButton.setOnClickListener {
            sqlRequest()
            // Put the String to pass back into an Intent and close this activity
            val intent = Intent()
            sqlRequest.clear()
            sqlRequest.append("SELECT * FROM estate   ;")
            intent.putExtra(SQL_REQUEST, sqlRequest.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        radioSold.setOnClickListener{
            inputDateSold.visibility = View.VISIBLE
        }

        radioSell.setOnClickListener{
            inputDateSold.visibility = View.GONE
        }

        filterButton.setButtonListener {
            sqlRequest()
            // Put the String to pass back into an Intent and close this activity
            val intent = Intent()
            intent.putExtra(SQL_REQUEST, sqlRequest.toString())
            intent.putExtra(FILTER_LIST_SHOW, listFilterShow)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun sqlRequest(){
        sqlRequest.append("SELECT * FROM estate WHERE ")
        if(minSurface.text.isNotEmpty()){
            listFilterShow.add("${minSurface.text}m2 min")
            sqlRequest.append("surface >= ${minSurface.text} AND ")
        }
        if(maxSurface.text.isNotEmpty()){
            listFilterShow.add("${maxSurface.text}m2 max")
            sqlRequest.append("surface < ${maxSurface.text} AND ")
        }
        if(minPrice.text.isNotEmpty()){
            listFilterShow.add("$${minPrice.text} min")
            sqlRequest.append("price >= ${minPrice.text} AND ")
        }
        if(maxPrice.text.isNotEmpty()){
            listFilterShow.add("$${maxPrice.text} max")
            sqlRequest.append("price <= ${maxPrice.text} AND ")
        }
        if(publishEdit.text.isNotEmpty()){
            listFilterShow.add("Published before ${convertForSQL(publishEdit.text.toString())}")
            sqlRequest.append("date_create > '${convertForSQL(publishEdit.text.toString())}' AND ")
        }
        if(proxyWork.isChecked){
            listFilterShow.add("Close to work")
            sqlRequest.append("proximity_address LIKE '%Work%' AND ")
        }
        if(proxySchool.isChecked){
            listFilterShow.add("Close to school")
            sqlRequest.append("proximity_address LIKE '%School%' AND ")
        }
        if(proxyStore.isChecked){
            listFilterShow.add("Close to store")
            sqlRequest.append("proximity_address LIKE '%Store%' AND ")
        }
        if(proxyParc.isChecked){
            listFilterShow.add("Close to parc")
            sqlRequest.append("proximity_address LIKE '%Parc%' AND ")
        }
        if (radioSell.isChecked){
            listFilterShow.add("Only sell")
            sqlRequest.append("status = 'sell' AND ")
        }
        if (radioSold.isChecked && inputDateSold.text.isNotEmpty()){
            listFilterShow.add("Only sold before ${inputDateSold.text}")
            sqlRequest.append("date_sold > ${convertForSQL(inputDateSold.text.toString())} AND ")
        }
        if(nbrPictureEdit.text.isNotEmpty()){
            listFilterShow.add("${nbrPictureEdit.text} min pictures")
            sqlRequest.append(" pictureList >= ${nbrPictureEdit.text} AND ")
        }

        if(locationEdit.text.isNotEmpty()){
            listFilterShow.add("arround : ${locationEdit.text}")
            sqlRequest.append("address LIKE  '%${locationEdit.text}%' AND ")
        }
    }

    private fun convertForSQL(date : String) : String{
        return date.replace("/","-")
    }
}
