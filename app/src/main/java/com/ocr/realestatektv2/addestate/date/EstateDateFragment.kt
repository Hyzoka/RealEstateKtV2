package com.ocr.realestatektv2.addestate.date

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_two_input_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateDateFragment : BaseComponentFragment<EstateDateViewModel>() {


    companion object {
        fun newInstance(listener: ComponentListener) =
            EstateDateFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_two_input_fragment
    override fun viewModel(): EstateDateViewModel { return getViewModel() }

    override fun setupView() {

        continueButton.setButtonListener {
            if (continueButton.isActive) {
                listener.onNext()
            }
        }

        sub.add(second_input.text.textChanges().subscribe {
            continueButton.isActive = it.isNotEmpty()
        })
    }

    override fun setupViewModel() {
    }

    override fun createView() {
    }
}