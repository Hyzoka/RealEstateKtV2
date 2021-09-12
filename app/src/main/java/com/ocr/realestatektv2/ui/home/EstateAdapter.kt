package com.ocr.realestatektv2.ui.home

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.EstateDetail
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.ui.detail.DetailActivity
import com.ocr.realestatektv2.util.ADDRESS
import com.ocr.realestatektv2.util.ESTATE
import com.ocr.realestatektv2.util.Utils.load
import org.jetbrains.anko.startActivity


class EstateAdapter(context: Context) : RecyclerView.Adapter<EstateAdapter.EstateViewHolder>() {

    private var estateList: List<Estate>? = null
    private var simpleList: List<EstateDetail>? = null
    private lateinit var estate: Estate
    lateinit var estateViewModel: EstateViewModel

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun setEstateList(movieList: List<Estate>?) {
        this.estateList = movieList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        val itemView = layoutInflater.inflate(R.layout.estate_item_list, parent, false)
        return EstateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        estateViewModel = ViewModelProvider(holder.itemView.context as AppCompatActivity).get(EstateViewModel::class.java)
        estateList?.let { list ->
             estate = list[position]


            var detailRoom = EstateDetail("🏠", estate.nbrRoom)
            var detailBed = EstateDetail("🛏", estate.nbrBedRoom)
            var detailBath = EstateDetail("🛀", estate.nbrBathRoom)
            var detailSurface = EstateDetail("🔛", estate.surface + "m2")
            var detailStatus = EstateDetail("💵", estate.status)
            simpleList = listOf(detailRoom, detailBed, detailBath, detailSurface, detailStatus)

            if (estate.status.isNotEmpty()) {
                if (estate.status == "sold") {
                    holder.iconSold.visibility = View.VISIBLE
                }
            }
            val simpleAdapter = FastItemAdapter<IItem<*, *>>()
            simpleAdapter.clear()
            simpleAdapter.add(simpleList?.map { DetailsItem(it) })

            holder.titleEstate.text = estate.typeEstate
            holder.priceEstate.text = "$" + estate.price
            holder.distanceEstate.text = estate.addresse

            estateViewModel.getPictureEstate(estate.id).observe(holder.itemView.context as AppCompatActivity,
                    Observer { pictureList: List<PictureEstate> ->
                        Log.i("SHOWPICTURE",pictureList.toString())
                        holder.imgEstate.load(pictureList[0].url, RequestOptions.centerCropTransform())

                    }
            )





            holder.rvEstate.layoutManager = LinearLayoutManager(
                holder.itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            holder.rvEstate.adapter = simpleAdapter

            holder.buttonCheckEstate.setOnClickListener {
                Log.i("ADAPTER",list[position].addresse)
                holder.itemView.context.startActivity<DetailActivity>(ESTATE to list[position].id, ADDRESS to list[position].addresse)
                (holder.itemView.context as Activity).overridePendingTransition(R.anim.slide_in, R.anim.fade_out)

            }
        }
    }

    override fun getItemCount(): Int {
        return if (estateList == null) {
            0
        } else {
            estateList!!.size
        }
    }

        class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgEstate: ImageView = itemView.findViewById(R.id.imgEstate)
            val titleEstate: TextView = itemView.findViewById(R.id.titleEstate)
            val priceEstate: TextView = itemView.findViewById(R.id.priceEstate)
            val distanceEstate: TextView = itemView.findViewById(R.id.distanceEstate)
            val rvEstate: RecyclerView = itemView.findViewById(R.id.rvEstate)
            val buttonCheckEstate: LinearLayout = itemView.findViewById(R.id.buttonCheckEstate)
            val iconSold : ImageView = itemView.findViewById(R.id.iconSold)
        }

    }