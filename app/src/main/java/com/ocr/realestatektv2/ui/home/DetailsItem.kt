package com.ocr.realestatektv2.ui.home

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.model.EstateDetail
import com.ocr.realestatektv2.util.KauIItem

class DetailsItem(var data: EstateDetail) : KauIItem<DetailsItem, DetailsItem.ViewHolder>(
    R.layout.detail_estate_item, { ViewHolder(it) },
    R.id.fastadapter_customlistitem_id) {

    @SuppressLint("SetTextI18n")
    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)

    }

    override fun unbindView(holder: ViewHolder) {
        holder.valuesEstate.text = data.values
        holder.emoji.text = data.emoji
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var valuesEstate = itemView.findViewById(R.id.valuesTV) as TextView
        var emoji = itemView.findViewById(R.id.emojiTv) as TextView
    }
}