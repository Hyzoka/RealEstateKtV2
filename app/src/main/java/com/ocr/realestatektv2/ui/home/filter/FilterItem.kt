package com.ocr.realestatektv2.ui.home.filter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.util.KauIItem

class FilterItem(var data: String) : KauIItem<FilterItem, FilterItem.ViewHolder>(
        R.layout.filter_estate_item, { ViewHolder(it) },
        R.id.fastadapter_customlistitem_id) {

    @SuppressLint("SetTextI18n")
    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)
        holder.txtEstate.text = data
    }

    override fun unbindView(holder: ViewHolder) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtEstate = itemView.findViewById(R.id.valuesTV) as TextView
    }
}