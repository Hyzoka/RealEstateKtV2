package com.ocr.realestatektv2.ui.map

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Transformations.map
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.base.BaseActivity

class MapsActivity : BaseActivity() {

    private lateinit var backButton : ImageView

    private val callback = OnMapReadyCallback { googleMap ->
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