package com.ocr.realestatektv2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.ocr.realestatektv2.util.NetworkStateReceiverListener
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity(), NetworkStateReceiverListener, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var imgDrawer : ImageView
    private lateinit var recyclerView: RecyclerView

    private lateinit var moviesListAdapter: EstateAdapter
    private lateinit var moviesViewModel: EstateViewModel
    private lateinit var moviesList: List<Estate>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_view)
        drawer = findViewById(R.id.drawer_layout)
        imgDrawer = findViewById(R.id.nav)
        recyclerView = findViewById(R.id.listRealEstate)

        initData()
        initAdapter()




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
            R.id.nav_item_add -> { startActivity<AddEstateFlow>()}
            R.id.nav_item_search -> startActivity<SimulatorActivity>()
            R.id.nav_item_maps -> startActivity<MapsActivity>()
        }
        return true
    }

    private fun initData() {
        moviesViewModel = ViewModelProvider(this).get(EstateViewModel::class.java)
        moviesViewModel.moviesList.observe(this,
            Observer { movies: List<Estate> ->
                moviesList = movies
                Log.i("DATA°INIT", moviesList[1].typeEstate)
                Log.i("DATA°INIT", moviesList[2].nbrRoom)
                Log.i("DATA°INIT", moviesList[3].surface)
                moviesListAdapter.setMovieList(movies)
            }
        )

    }

    private fun initAdapter(){
        moviesListAdapter = EstateAdapter(this)
        recyclerView.adapter = moviesListAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
    }
}