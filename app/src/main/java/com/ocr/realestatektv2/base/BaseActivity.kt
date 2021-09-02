package com.ocr.realestatektv2.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.ocr.realestatektv2.ui.home.EstateViewModel
import com.ocr.realestatektv2.util.ADDRESS
import com.ocr.realestatektv2.util.NetworkStateReceiverListener
import com.ocr.realestatektv2.util.Utils
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.io.IOException
import java.util.*

open class BaseActivity: AppCompatActivity(), NetworkStateReceiverListener {

    protected val sharedPreferences: SharedPreferences by inject()

    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var locationPermissionGranted = false
    var map: GoogleMap? = null
    var lastKnownLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var estateViewModel: EstateViewModel


    protected val NetworkStateReceiverListener.isConnected: Boolean
        get() {
            this as BaseActivity
            return Utils.isConnected(this)
        }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerConnectivityMonitoring(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationPermission()
        getCurrentLocation()
        initViewModel()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerConnectivityMonitoring(listener: NetworkStateReceiverListener) {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                listener.networkConnectivityChanged()
            }

            override fun onLost(network: Network) {
                listener.networkConnectivityChanged()
            }
        }
        this.networkCallback = networkCallback
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun unregisterConnectivityMonitoring() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = this.networkCallback ?: return
        connectivityManager.unregisterNetworkCallback(networkCallback)
        this.networkCallback = null
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDestroy() {
        super.onDestroy()
        unregisterConnectivityMonitoring()
    }

    override fun onResume() {
        super.onResume()
        if (!isConnected)
            this.networkConnectivityChanged()// call to show no network banner on activity resume
    }

    /**
     * LOCATION
     **/

    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation
                .addOnSuccessListener(this,
                    OnSuccessListener<Location?> { location ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            lastKnownLocation = location
                            sharedPreferences.edit().putString(ADDRESS, getAddress(lastKnownLocation!!.latitude,lastKnownLocation!!.longitude).toString()).apply()
                        }
                    })
    }

     private fun initViewModel() { estateViewModel = ViewModelProvider(this).get(EstateViewModel::class.java) }

        fun moveMap(gMap: GoogleMap, latitude: Double, longitude: Double) {
        val latlng = LatLng(latitude, longitude)
        val cu = CameraUpdateFactory.newLatLngZoom(latlng, 15f)
        gMap.addMarker(MarkerOptions().position(latlng))
        gMap.moveCamera(cu)
    }
    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    interface Callback {
        fun call()
    }

    private fun getAddress(latitude: Double, longitude: Double): String? {
        val result = StringBuilder()
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val address = addresses[0]
                result.append(address.locality).append(",")
                result.append(address.countryCode)
            }
        } catch (e: IOException) {
            Log.e("tag", e.message.toString())
        }
        return result.toString()
    }
}
