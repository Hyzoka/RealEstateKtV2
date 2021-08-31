package com.ocr.realestatektv2.ui.home

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.EstateDetail
import com.ocr.realestatektv2.util.Utils.load

class EstateAdapter(context: Context) : RecyclerView.Adapter<EstateAdapter.EstateViewHolder>() {

    private var estateList: List<Estate>? = null
    private var simpleList: List<EstateDetail>? = null

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val simpleAdapter = FastItemAdapter<IItem<*, *>>()

    fun setMovieList(movieList: List<Estate>?) {
        this.estateList = movieList
        notifyDataSetChanged()
    }

    private fun initSimpleAdapter() {
        simpleAdapter.clear()
        simpleAdapter.add(simpleList?.map { DetailsItem(it) })
        simpleAdapter.notifyAdapterDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        val itemView = layoutInflater.inflate(R.layout.estate_item_list, parent, false)
        return EstateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        estateList?.let { list ->
            val estate = list[position]
            var detailRoom = EstateDetail("🏠",estate.nbrRoom)
            var detailBed = EstateDetail("🛏",estate.nbrBedRoom)
            var detailBath = EstateDetail("🛀",estate.nbrBathRoom)
            var detailSurface = EstateDetail("🔛",estate.surface + "m2")
            var detailStatus = EstateDetail("💵",estate.status)
            simpleList = listOf(detailRoom,detailBed,detailBath,detailSurface,detailStatus)
            initSimpleAdapter()
            holder.titleEstate.text = estate.typeEstate
            holder.priceEstate.text = "$" + estate.price
            holder.distanceEstate.text = estate.addresse
            holder.imgEstate.load(estate.picture)


            holder.rvEstate.layoutManager = LinearLayoutManager(holder.itemView.context,LinearLayoutManager.HORIZONTAL, false)
            holder.rvEstate.adapter = simpleAdapter

//                holder.itemView.setOnClickListener {
//                    val dialogFragment: DialogFragment = newInstance(movie.title, directorFullName)
//                    dialogFragment.setTargetFragment(parent, 99)
//                    dialogFragment.show(
//                        (parent.activity as AppCompatActivity).supportFragmentManager,
//                        MovieSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE
//                    )
//                }
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
        }

    }