package com.ocr.realestatektv2.ui.detail

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.EstateDetail
import com.ocr.realestatektv2.ui.home.DetailsItem
import com.ocr.realestatektv2.util.ESTATE
import com.ocr.realestatektv2.util.Utils.load
import kotlinx.android.synthetic.main.detail_activity.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class DetailActivity  : BaseComponentFragment<DetailViewModel>(), ComponentListener{

    private var simpleList: List<EstateDetail>? = null
    private val simpleAdapter = FastItemAdapter<IItem<*, *>>()
    private var pictureList: List<String>? = null
    private val pictureAdapter = FastItemAdapter<IItem<*, *>>()
    private var recyclerViewSimple: RecyclerView? = null
    private var recyclerViewPicture: RecyclerView? = null

    companion object {
        fun newInstance(listener: ComponentListener) =
            DetailActivity().apply { this.listener = listener }
    }

    override fun viewModel(): DetailViewModel {return getViewModel() }

    override fun setupViewModel() {
    }

    override fun layoutId() = R.layout.detail_activity

    override fun setupView() {
        val data = requireArguments().getInt(ESTATE)

        estateViewModel.estateList.observe(this,
            Observer { estate: List<Estate> ->
                setData(estate[data - 1])
            })
        setupListener()


    }

    private fun setupListener(){
        fragment.setOnClickListener {
            listener.updateData()
        }
    }

    private fun setData(data: Estate){
        Log.i("DATA_TEST", data.typeEstate)
        Log.i("DATA_TEST", data.price)
        Log.i("DATA_TEST", data.nbrRoom)
        imgEstate.load(data.picture)
        titleEstate.text = data.typeEstate
        priceEstate.text = data.price
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

    override fun onNext(data: Any?) {
    }

    override fun updateData() {
        simpleAdapter.notifyAdapterDataSetChanged()
        pictureAdapter.notifyAdapterDataSetChanged()
    }

    override fun createView() {
        recyclerViewSimple = mainView.findViewById(R.id.rvEstate)
        recyclerViewSimple!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSimple!!.adapter = simpleAdapter

        recyclerViewPicture = mainView.findViewById(R.id.rvPictureEstate)
        recyclerViewPicture!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPicture!!.adapter = pictureAdapter
    }
}