package com.ocr.realestatektv2.ui.home

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.AddEstateFlow
import com.ocr.realestatektv2.base.BaseActivity
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.ui.map.MapsActivity
import com.ocr.realestatektv2.ui.simulator.SimulatorActivity
import com.ocr.realestatektv2.util.ADDRESS
import com.ocr.realestatektv2.util.NetworkStateReceiverListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*


class MainActivity : BaseActivity(), NetworkStateReceiverListener, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var imgDrawer : ImageView
    private lateinit var recyclerView: RecyclerView

    private lateinit var estateListAdapter: EstateAdapter
    private lateinit var estatesList: List<Estate>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_view)
        drawer = findViewById(R.id.drawer_layout)
        imgDrawer = findViewById(R.id.nav)
        recyclerView = findViewById(R.id.listRealEstate)

        initData()
        initAdapter()
            city.text = sharedPreferences.getString(ADDRESS,"Los Angeles,CA")
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
            Toast.makeText(this, "HAVE WIFI", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "DONT HAVE WIFI", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_add -> {
                startActivity<AddEstateFlow>()
            }
            R.id.nav_item_search -> startActivity<SimulatorActivity>()
            R.id.nav_item_maps -> startActivity<MapsActivity>()
        }
        return true
    }

    private fun initData() {
        estateViewModel.estateList.observe(this,
            Observer { estate: List<Estate> ->
                estatesList = estate
                if (estatesList.isNotEmpty()) {
                    estateListAdapter.setEstateList(estate)
                }
            }
        )

    }

    override fun onResume() {
        super.onResume()
        city.text = sharedPreferences.getString(ADDRESS,"Los Angeles,CA")
    }

    private fun initAdapter(){
        estateListAdapter = EstateAdapter(this)
        recyclerView.adapter = estateListAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}