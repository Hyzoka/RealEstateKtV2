package com.ocr.realestatektv2.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.AddEstateFlow
import com.ocr.realestatektv2.base.EstateBaseActivity
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.EstateDetail
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.ui.home.DetailsItem
import com.ocr.realestatektv2.util.ADDRESS
import com.ocr.realestatektv2.util.EDIT_ESTATE
import com.ocr.realestatektv2.util.ESTATE
import com.ocr.realestatektv2.util.FROM_DETAIL
import com.ocr.realestatektv2.util.Utils.load
import kotlinx.android.synthetic.main.detail_activity.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailActivity  : EstateBaseActivity<DetailViewModel>(){

    private var simpleList = arrayListOf<EstateDetail>()
    private val simpleAdapter = FastItemAdapter<IItem<*, *>>()

    private val pictureAdapter = FastItemAdapter<IItem<*, *>>()
    private var editEstate : Int = 0
    private var address : String? = null

    private val callback = OnMapReadyCallback { googleMap ->

        Log.i("ADAPTER_DETAIL",intent.getStringExtra(ADDRESS).toString())

        if (intent.getStringExtra(ADDRESS) != null) {
            moveMap(googleMap, getGps(intent.getStringExtra(ADDRESS)!!).latitude,getGps(intent.getStringExtra(ADDRESS)!!).longitude)

        }
        else{
            moveMap(googleMap, 34.003342, -118.485832) // <- Los Angeles location
        }
    }

    override fun viewModel(): DetailViewModel {
        return getViewModel()
    }

    override fun setupViewModel() {
        initSimpleAdapter()
        val data = intent.getIntExtra(ESTATE,36)
        editEstate =  data

        estateViewModel.estateList.observe(this,

            Observer { estateList: List<Estate> ->
                for (estate in estateList) {
                    if (estate.id == data){
                        setData(estate)
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(callback)
        setupListEstate()
        setupListPicture()
        setupListener()
    }

    private fun setupListener(){
        back.setOnClickListener {
            finish()
        }

        edit.setOnClickListener {
            startActivity<AddEstateFlow>(EDIT_ESTATE to editEstate, FROM_DETAIL to true)
        }
    }

    private fun setupListEstate(){
        rvEstate.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvEstate.adapter = simpleAdapter


    }

    private fun setupListPicture(){
        rvPictureEstate.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPictureEstate.adapter = pictureAdapter
    }

    private fun setData(data: Estate){

        imgEstate.load(data.picture[0].url, RequestOptions.centerCropTransform())
        titleEstate.text = data.typeEstate
        priceEstate.text = "$${data.price}"
        distanceEstate.text = data.addresse
        address = data.addresse
        descEstate.text = data.description

        // Recycler
        var detailRoom = EstateDetail("🏠", data.nbrRoom)
        var detailBed = EstateDetail("🛏", data.nbrBedRoom)
        var detailBath = EstateDetail("🛀", data.nbrBathRoom)
        var detailSurface = EstateDetail("🔛", data.surface + "m2")
        var detailStatus = EstateDetail("💵", data.status)

        simpleList = arrayListOf(detailRoom, detailBed, detailBath, detailSurface, detailStatus)
        simpleAdapter.clear()
        simpleAdapter.add(simpleList.map { DetailsItem(it) })
        simpleAdapter.notifyAdapterDataSetChanged()
        Log.i("PICTURE_DETAIL",data.picture.toString())
        initPictureAdapter()
        pictureAdapter.clear()
        pictureAdapter.add(data.picture.map { PictureItem(it) })
        pictureAdapter.notifyAdapterDataSetChanged()

    }

    private fun initSimpleAdapter() {

    }

    private fun initPictureAdapter() {

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_down)
    }
}