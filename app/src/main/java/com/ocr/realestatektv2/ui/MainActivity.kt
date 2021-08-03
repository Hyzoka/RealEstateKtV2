package com.ocr.realestatektv2.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.base.BaseActivity
import com.ocr.realestatektv2.ui.map.MapsActivity
import com.ocr.realestatektv2.ui.simulator.SimulatorActivity
import com.ocr.realestatektv2.util.NetworkStateReceiverListener
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NetworkStateReceiverListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var imgDrawer : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_view)

        drawer = findViewById(R.id.drawer_layout)
        imgDrawer = findViewById(R.id.nav)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        setupListener()

    }

    private fun setupListener(){
        imgDrawer.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }
    }

    override fun networkConnectivityChanged() {
        if (isConnected){
            Toast.makeText(this,"HAVE WIFI", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"DONT HAVE WIFI", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_add -> {
            }
            R.id.nav_item_search -> startActivity<SimulatorActivity>()
            R.id.nav_item_maps -> startActivity<MapsActivity>()
        }
        return true
    }
}