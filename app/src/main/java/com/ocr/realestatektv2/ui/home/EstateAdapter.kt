package com.ocr.realestatektv2.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ocr.realestatektv2.model.Estate

class EstateAdapter: ListAdapter<Estate, RecyclerView.ViewHolder>(SKI_RESORT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EstateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as EstateViewHolder).bind(repoItem)
        }
    }


    companion object {
        private val SKI_RESORT_COMPARATOR = object : DiffUtil.ItemCallback<Estate>() {
            override fun areItemsTheSame(oldItem: Estate, newItem: Estate): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Estate, newItem: Estate): Boolean =
                oldItem.equals(newItem)
        }
    }
}