package com.ocr.realestatektv2.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.model.Estate

class EstateViewHolder (view: View)  : RecyclerView.ViewHolder(view) {
    private val imgEstate: TextView = view.findViewById(R.id.imgEstate)
    private val titleEstate: TextView = view.findViewById(R.id.titleEstate)
    private val priceEstate: TextView = view.findViewById(R.id.priceEstate)
    private val distanceEstate: TextView = view.findViewById(R.id.distanceEstate)
    private val rvEstate: RecyclerView = view.findViewById(R.id.rvEstate)
    private val buttonCheckEstate: LinearLayout = view.findViewById(R.id.buttonCheckEstate)

    fun bind(skiResort: Estate?) {
        if (skiResort != null) {
            showSkiResortData(skiResort)
        }
    }

    private fun showSkiResortData(skiResort: Estate) {
        skiResort.apply {
            titleEstate.text = typeEstate
            priceEstate.text = price
            distanceEstate.text = addresse
        }
    }

    companion object {
        fun create(parent: ViewGroup): EstateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_item_list, parent, false)
            return EstateViewHolder(view)
        }
    }
}