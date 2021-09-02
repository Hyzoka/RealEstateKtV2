package com.ocr.realestatektv2.addestate.rooms

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.addestate.price.EstatePriceSizeViewModel
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_three_input_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateRoomsFragment  : BaseComponentFragment<EstatePriceSizeViewModel>() {

    companion object {
        fun newInstance(listener: ComponentListener) =
                EstateRoomsFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_three_input_fragment
    override fun viewModel(): EstatePriceSizeViewModel { return getViewModel() }

    override fun setupView() {

        continueButton.setButtonListener {
            if (continueButton.isActive) {
                listener.onNext()
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
}