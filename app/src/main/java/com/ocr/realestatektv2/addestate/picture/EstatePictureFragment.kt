package com.ocr.realestatektv2.addestate.picture

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Picture
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
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
    private var pictureListEdit = arrayListOf<PictureEstate>()
    private var editPicture = false
    private val pictureAdapter = FastItemAdapter<IItem<*, *>>()
    private var imagePath = ""
    private var firstID = 0
    companion object {
        fun newInstance(listener: ComponentListener) =
            EstatePictureFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_picture_fragment
    override fun viewModel(): EstatePictureViewModel { return getViewModel() }

    override fun setupView() {
        firstID =  estateViewModel.getIdMaxPicture()+1

        if (pictureList.isNotEmpty()){
            nextFrag.isActive = true
            nextFrag.visibility = View.VISIBLE
        }
        initData()
        openCam.setButtonListener {
            openCam()
        }

        nextFrag.setButtonListener {
            if (editPicture){
                listener.onNext(pictureListEdit)
            }
            else{
            listener.onNext(pictureList)
            }
        }

    }

    private fun setupListPicture() {
        pictRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
                returnValue.first()?.let { picture ->
                    imagePath = picture
                    showdialog()
                }
            }
        }
    }

    fun setPictureEdit(picture : List<PictureEstate>){
        pictureList = picture as ArrayList<PictureEstate>
        Log.i("EDITPITURE",picture.toString())
    }
    fun setEditPicture(edit : Boolean){
        editPicture = edit
    }

    private fun initData(){

        pictureAdapter.clear()
        pictureAdapter.add(pictureList.map { PictureItem(it) })
        pictureAdapter.notifyAdapterDataSetChanged()
        setupListPicture()
    }

   private fun showdialog(){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Description")

// Set up the input
        val input = EditText(context)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Living room")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            pictureList.add(PictureEstate(firstID + pictureList.size,1,input.text.toString(),imagePath))
            pictureListEdit.add(PictureEstate(firstID + pictureList.size,1,input.text.toString(),imagePath))
            initData()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

}