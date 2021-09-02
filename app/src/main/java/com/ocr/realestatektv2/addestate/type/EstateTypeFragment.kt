package com.ocr.realestatektv2.addestate.type

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_one_input_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateTypeFragment  : BaseComponentFragment<EstateTypeViewModel>() {


    companion object {
        fun newInstance(listener: ComponentListener) =
                EstateTypeFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_one_input_fragment
    override fun viewModel(): EstateTypeViewModel { return getViewModel() }

    override fun setupView() {

        continueButton.setButtonListener {
            if (continueButton.isActive) {
                listener.onNext(first_input.text.text.toString())
            }
        }

        sub.add(first_input.text.textChanges().subscribe {
            continueButton.isActive = it.isNotEmpty()
        })

    }

    override fun setupViewModel() {
        first_input.hintText(getString(R.string.type_estate_add))
    }
}