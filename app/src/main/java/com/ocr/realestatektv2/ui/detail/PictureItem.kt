package com.ocr.realestatektv2.ui.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.util.KauIItem

class PictureItem(var data: String) : KauIItem<PictureItem, PictureItem.ViewHolder>(
    R.layout.picture_estate_item, { ViewHolder(it) },
    R.id.fastadapter_customlistitem_id) {

    @SuppressLint("SetTextI18n")
    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)

    }

    override fun unbindView(holder: ViewHolder) {
        holder.valuesEstate.setImageURI(Uri.parse(data))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var valuesEstate = itemView.findViewById(R.id.imgPicture) as ImageView
    }
}