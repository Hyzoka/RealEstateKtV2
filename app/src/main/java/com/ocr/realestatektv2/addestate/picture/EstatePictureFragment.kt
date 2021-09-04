package com.ocr.realestatektv2.addestate.picture

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Picture
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import com.ocr.realestatektv2.util.PIX_REQUEST_CODE
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.ui.detail.PictureItem
import com.ocr.realestatektv2.util.Utils.load
import kotlinx.android.synthetic.main.estate_picture_fragment.*

@Suppress("DEPRECATION")
class EstatePictureFragment : BaseComponentFragment<EstatePictureViewModel>() {

    private var pictureList = arrayListOf<PictureEstate>()
    private val pictureAdapter = FastItemAdapter<IItem<*, *>>()
    companion object {
        fun newInstance(listener: ComponentListener) =
            EstatePictureFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_picture_fragment
    override fun viewModel(): EstatePictureViewModel { return getViewModel() }

    override fun setupView() {
        openCam.setButtonListener {
            openCam()
        }

        nextFrag.setButtonListener {
            listener.onNext(pictureList)
        }

    }

    private fun setupListPicture() {
        pictRv.layoutManager = LinearLayoutManager(context)
        pictRv.adapter = pictureAdapter
    }

    override fun setupViewModel() {
        openCam.isActive = true
    }

    private fun openCam(){
        val options = Options.init().setRequestCode(PIX_REQUEST_CODE).setFrontfacing(true)
        Pix.start(this, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == PIX_REQUEST_CODE) {
            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            nextFrag.visibility = View.VISIBLE
            nextFrag.isActive = true
            if (returnValue?.first() != null) {
                returnValue.first()?.let { imagePath ->
                    pictureList.add(PictureEstate(0,"picture",imagePath))
                    pictureAdapter.clear()
                    pictureAdapter.add(pictureList.map { PictureItem(it.url) })
                    setupListPicture()
                    Log.i("REQUESTOK",pictureList.toString())
                }
            }
        }
    }

    fun setPictureEdit(pictureList : String){

    }
}