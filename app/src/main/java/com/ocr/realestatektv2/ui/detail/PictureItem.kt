package com.ocr.realestatektv2.ui.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.util.KauIItem
import com.ocr.realestatektv2.util.Utils.load

class PictureItem(var data: PictureEstate) : KauIItem<PictureItem, PictureItem.ViewHolder>(
    R.layout.picture_estate_item, { ViewHolder(it) },
    R.id.fastadapter_customlistitem_id) {

    @SuppressLint("SetTextI18n")
    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)
        holder.valuesEstate.load(data.url, RequestOptions.centerCropTransform())
        holder.txtEstate.text = data.pictureDescription
    }

    override fun unbindView(holder: ViewHolder) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var valuesEstate = itemView.findViewById(R.id.imgPicture) as ImageView
        var txtEstate = itemView.findViewById(R.id.txt) as TextView
    }
}