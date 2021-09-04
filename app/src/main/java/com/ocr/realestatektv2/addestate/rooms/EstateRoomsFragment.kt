package com.ocr.realestatektv2.addestate.rooms

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.addestate.price.EstatePriceSizeViewModel
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_three_input_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateRoomsFragment  : BaseComponentFragment<EstatePriceSizeViewModel>() {

    private var roomsEdit : String? = null
    private var roomsBedEdit : String? = null
    private var roomsBathEdit : String? = null
    companion object {
        fun newInstance(listener: ComponentListener) =
                EstateRoomsFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_three_input_fragment
    override fun viewModel(): EstatePriceSizeViewModel { return getViewModel() }

    override fun setupView() {
        if (roomsEdit != null){
        first_input.text.setText(roomsEdit)
        second_input.text.setText(roomsBedEdit)
        three_input.text.setText(roomsBathEdit)
        }
        continueButton.setButtonListener {
            if (continueButton.isActive) {
                listener.onNext(arrayListOf(first_input.text.text.toString(),second_input.text.text.toString(),three_input.text.text.toString()))
            }
        }

        sub.add(three_input.text.textChanges().subscribe {
            continueButton.isActive = it.isNotEmpty()
        })
    }

    override fun setupViewModel() {
        first_input.numberKeyboard = true
        second_input.numberKeyboard = true
        three_input.numberKeyboard = true
    }

    fun setRoomsEdit(roomsList : ArrayList<String>){
        roomsEdit = roomsList[0]
        roomsBedEdit = roomsList[1]
        roomsBathEdit = roomsList[2]
    }
}