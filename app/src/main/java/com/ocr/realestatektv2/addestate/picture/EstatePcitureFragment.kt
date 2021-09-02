package com.ocr.realestatektv2.addestate.picture

import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import com.ocr.realestatektv2.util.PIX_REQUEST_CODE
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.ocr.realestatektv2.util.Utils
import com.ocr.realestatektv2.util.Utils.load
import kotlinx.android.synthetic.main.estate_picture_fragment.*

class EstatePcitureFragment : BaseComponentFragment<EstatePictureViewModel>() {

    companion object {
        fun newInstance(listener: ComponentListener) =
            EstatePcitureFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_picture_fragment
    override fun viewModel(): EstatePictureViewModel { return getViewModel() }

    override fun setupView() {
        openCam.setButtonListener {
            openCam()
        }



    }

    override fun setupViewModel() {
        openCam.isActive = true
    }

    private fun openCam(){
        val options = Options.init().setRequestCode(PIX_REQUEST_CODE).setFrontfacing(true)
        Pix.start(activity, options)
    }

    override fun createView() {
    }
}