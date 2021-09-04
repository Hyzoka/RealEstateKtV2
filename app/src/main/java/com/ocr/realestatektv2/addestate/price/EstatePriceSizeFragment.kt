package com.ocr.realestatektv2.addestate.price

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_two_input_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstatePriceSizeFragment  : BaseComponentFragment<EstatePriceSizeViewModel>() {

    private var priceEdit : String? = null
    private var surfaceEdit : String? = null

    companion object {
        fun newInstance(listener: ComponentListener) =
            EstatePriceSizeFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_two_input_fragment
    override fun viewModel(): EstatePriceSizeViewModel { return getViewModel() }

    override fun setupView() {
        if (priceEdit != null) {
            first_input.text.setText(priceEdit)
            second_input.text.setText(surfaceEdit)
        }
        continueButton.setButtonListener {
            if (continueButton.isActive) {
                listener.onNext(arrayListOf(first_input.text.text.toString(),second_input.text.text.toString()))
            }
        }

        sub.add(second_input.text.textChanges().subscribe {
            continueButton.isActive = it.isNotEmpty()
        })

    }

    override fun setupViewModel() {
        first_input.hintText(getString(R.string.price_estate))
        second_input.hintText(getString(R.string.area_of_propertie))

        first_input.numberKeyboard = true
        second_input.numberKeyboard = true
    }

    fun setPriceSizeEdit(priceSize : ArrayList<String>){
        priceEdit = priceSize[0]
        surfaceEdit = priceSize[1]
    }
}