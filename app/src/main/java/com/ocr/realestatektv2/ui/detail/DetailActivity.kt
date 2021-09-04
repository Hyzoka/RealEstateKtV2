package com.ocr.realestatektv2.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.AddEstateFlow
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseActivity
import com.ocr.realestatektv2.base.BaseComponentFragment
import com.ocr.realestatektv2.base.EstateBaseActivity
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.EstateDetail
import com.ocr.realestatektv2.ui.home.DetailsItem
import com.ocr.realestatektv2.util.EDIT_ESTATE
import com.ocr.realestatektv2.util.ESTATE
import com.ocr.realestatektv2.util.FROM_DETAIL
import com.ocr.realestatektv2.util.Utils.load
import kotlinx.android.synthetic.main.detail_activity.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel


class DetailActivity  : EstateBaseActivity<DetailViewModel>(){

    private var simpleList: List<EstateDetail>? = null
    private val simpleAdapter = FastItemAdapter<IItem<*, *>>()

    private var pictureList: List<String>? = null
    private val pictureAdapter = FastItemAdapter<IItem<*, *>>()
    private var editEstate : Int = 0

    override fun viewModel(): DetailViewModel {
        return getViewModel()
    }

    override fun setupViewModel() {
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

        imgEstate.load(data.picture, RequestOptions.centerCropTransform())
        titleEstate.text = data.typeEstate
        priceEstate.text = "$${data.price}"
        distanceEstate.text = data.addresse
        descEstate.text = data.description

        // Recycler
        var detailRoom = EstateDetail("🏠", data.nbrRoom)
        var detailBed = EstateDetail("🛏", data.nbrBedRoom)
        var detailBath = EstateDetail("🛀", data.nbrBathRoom)
        var detailSurface = EstateDetail("🔛", data.surface + "m2")
        var detailStatus = EstateDetail("💵", data.status)
        simpleList = listOf(detailRoom, detailBed, detailBath, detailSurface, detailStatus)

        pictureList = listOf(data.picture)
        initPictureAdapter()
        initSimpleAdapter()

    }

    private fun initSimpleAdapter() {
        simpleAdapter.clear()
        simpleAdapter.add(simpleList?.map { DetailsItem(it) })
        simpleAdapter.notifyAdapterDataSetChanged()
    }

    private fun initPictureAdapter() {
        pictureAdapter.clear()
        pictureAdapter.add(pictureList?.map { PictureItem(it) })
        pictureAdapter.notifyAdapterDataSetChanged()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_down)
    }
}