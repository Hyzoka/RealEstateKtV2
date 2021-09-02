package com.ocr.realestatektv2.ui.map

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.base.BaseActivity
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.ui.detail.DetailActivity
import com.ocr.realestatektv2.util.ESTATE
import org.jetbrains.anko.startActivity


class MapsActivity : BaseActivity() {

    private lateinit var estatesList: List<Estate>
    private lateinit var backButton : ImageView

    private val callback = OnMapReadyCallback { googleMap ->

        estateViewModel.estateList.observe(this,
            Observer { estate: List<Estate> ->
                estatesList = estate
                if (estatesList.isNotEmpty()) {
                    estatesList.forEach {
                        googleMap.addMarker(MarkerOptions().position(getGps(it.addresse)).snippet(it.id.toString()))
                    }
                }
                googleMap.setOnMarkerClickListener { marker ->
                    if (marker.snippet != null){
                    startActivity<DetailActivity>(ESTATE to marker.snippet.toInt())
                    overridePendingTransition(R.anim.slide_in, R.anim.fade_out)}
                    else{
                        marker.title = "Your current location"
                    }
                    false
                }
            }
        )
        if (lastKnownLocation != null) {
            map = googleMap
            moveMap(googleMap, lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
        }
        else{
            moveMap(googleMap, 34.003342, -118.485832) // <- Los Angeles location
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps)
        setupListener()


        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(callback)

    }


    private fun setupListener(){
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
