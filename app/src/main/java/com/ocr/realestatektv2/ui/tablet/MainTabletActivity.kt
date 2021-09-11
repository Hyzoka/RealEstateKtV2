package com.ocr.realestatektv2.ui.tablet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.AddEstateFlow
import com.ocr.realestatektv2.addestate.type.EstateTypeFragment
import com.ocr.realestatektv2.base.BaseActivity
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.ui.home.EstateAdapter
import com.ocr.realestatektv2.ui.home.filter.FilterActivity
import com.ocr.realestatektv2.ui.home.filter.FilterItem
import com.ocr.realestatektv2.ui.map.MapsActivity
import com.ocr.realestatektv2.ui.simulator.SimulatorActivity
import com.ocr.realestatektv2.util.*
import kotlinx.android.synthetic.main.activity_add_estate.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.notificationManager
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class MainTabletActivity : BaseActivity(), NetworkStateReceiverListener, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var imgDrawer : ImageView
    private lateinit var recyclerView: RecyclerView


    private var currentFragment: Fragment? = null

    private lateinit var detailFragment : DetailFragmentTablet

    private lateinit var estateListAdapter: EstateAdapter
    private val filterAdapter = FastItemAdapter<IItem<*, *>>()
    private lateinit var estatesList: List<Estate>

    private var sizeList = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_view)
        drawer = findViewById(R.id.drawer_layout)
        imgDrawer = findViewById(R.id.nav)
        recyclerView = findViewById(R.id.listRealEstate)
        if (intent.getBooleanExtra(SHOW_POP,false)){
            notificationManager.sendNotification(
                    this.getText(R.string.estate_add_desc).toString(),
                    this
            )
        }
        initData()
        initAdapter()
        city.text = sharedPreferences.getString(ADDRESS,"Los Angeles,CA")
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        createChannel(getString(R.string.estate_add_id),getString(R.string.app_name))
        setupListener()

    }

    private fun setupListener(){
        imgDrawer.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        filter.setOnClickListener {
            startActivityForResult<FilterActivity>(FILTER_ACTIVITY_REQUEST_CODE)
        }
    }

    // This method is called when the second activity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check that it is the SecondActivity with an OK result
        if (requestCode == FILTER_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                val sqlRequest = data!!.getStringExtra(SQL_REQUEST)
                sqlRequest?.dropLast(sqlRequest.length) + ";"
                val  listFilter = data.getStringArrayListExtra(FILTER_LIST_SHOW)

                if (listFilter!!.size > 0) {
                    filterList.visibility = View.VISIBLE
                    lottie.visibility = View.GONE
                    filterList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    filterList.adapter = filterAdapter
                    filterAdapter.clear()
                    filterAdapter.add(listFilter?.map { FilterItem(it) })
                    filterAdapter.notifyAdapterDataSetChanged()


                    estateListAdapter.setEstateList(estateViewModel.getFilterList("${sqlRequest!!.dropLast(4)};"))

                    Log.i("SQL_REQUEST_0",sqlRequest?.dropLast(4).trim()+ ";")
                    Log.i("SQL_REQUEST", estateViewModel.getFilterList("${sqlRequest!!.dropLast(4).trim()};").toString())
                }
                else{
                    filterList.visibility = View.GONE
                    lottie.visibility = View.VISIBLE
                    initData()
                }
            }
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
                startActivity<AddEstateFlow>(ID_ESTATE to sizeList)
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
                        sizeList = estate.size
                        estateListAdapter.setEstateList(estate.reversed())
                        Log.i("ESTATE_DATA",estate.toString())
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
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    // TODO: Step 2.4 change importance
                    NotificationManager.IMPORTANCE_HIGH
            )// TODO: Step 2.6 disable badges for this channel
                    .apply {
                        setShowBadge(false)
                    }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.estate_add)

            val notificationManager = this.getSystemService(
                    NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create a channel
    }


    // INIT DETAIL

    private fun initFlow() {
        initFragments()
    }


    private fun initFragments() {
        currentFragment = detailFragment
        showCurrentFragment()
    }

    private fun showCurrentFragment() {
        currentFragment?.let { fragment ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            )
            if (!fragment.isAdded) {
                transaction.add(R.id.fragment_content, fragment)
                transaction.addToBackStack("")
            } else {
                transaction.replace(R.id.fragment_content, fragment)
            }
            transaction.commit()
        }
    }
}