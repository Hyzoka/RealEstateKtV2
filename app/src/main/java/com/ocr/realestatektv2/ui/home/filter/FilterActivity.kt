package com.ocr.realestatektv2.ui.home.filter

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.base.BaseActivity
import kotlinx.android.synthetic.main.filter_activity.*
import kotlinx.android.synthetic.main.filter_activity.proxyParc
import kotlinx.android.synthetic.main.filter_activity.proxySchool
import kotlinx.android.synthetic.main.filter_activity.proxyStore
import kotlinx.android.synthetic.main.filter_activity.proxyWork

class FilterActivity : BaseActivity(){

    val sqlRequest = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_activity)
        filterButton.isActive = true
        setupListener()

    }

    private fun setupListener(){
        back.setOnClickListener {
            finish()
        }

        radioSold.setOnClickListener{
            inputDateSold.visibility = View.VISIBLE
            inputDateSold.hintText("2021/01/01")
        }

        radioSell.setOnClickListener{
            inputDateSold.visibility = View.GONE
        }

        filterButton.setButtonListener {

            // Put the String to pass back into an Intent and close this activity
            val intent = Intent()
            intent.putExtra("keyName", "stringToPassBack")
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun sqlRequest(){
        sqlRequest.append("SELECT * FROM estate ")
        if(minSurface.text.isNotEmpty()){
            sqlRequest.append("WHERE surface >= ${minSurface.text},")
        }
        if(maxSurface.text.isNotEmpty()){
            sqlRequest.append("WHERE surface =< ${maxSurface.text},")
        }
        if(minPrice.text.isNotEmpty()){
            sqlRequest.append("WHERE price >= ${minPrice.text},")
        }
        if(maxPrice.text.isNotEmpty()){
            sqlRequest.append("WHERE price =< ${maxPrice.text},")

        }
        if(proxyWork.isChecked){
            sqlRequest.append("WHERE proximity_address = work,")
        }
        if(proxySchool.isChecked){
            sqlRequest.append("school")
        }
        if(proxyStore.isChecked){
            sqlRequest.append("store")
        }
        if(proxyParc.isChecked){
            sqlRequest.append("parc")
        }
        if (radioSell.isChecked){
            sqlRequest.append("sell")
        }
        if (radioSold.isChecked && inputDateSold.text.text.isNotEmpty()){

        }
    }

    private fun arroundLocation(){
        if (locationEdit.text.isNotEmpty()){
            // get current location, compare this to edit
            }
        }
}
