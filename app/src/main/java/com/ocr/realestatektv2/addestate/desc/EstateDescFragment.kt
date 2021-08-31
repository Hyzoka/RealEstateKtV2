package com.ocr.realestatektv2.addestate.desc

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_desc_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateDescFragment : BaseComponentFragment<EstateDescViewModel>() {

    companion object {
        fun newInstance(listener: ComponentListener) =
            EstateDescFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_desc_fragment
    override fun viewModel(): EstateDescViewModel { return getViewModel() }

    override fun setupView() {

        continueDescButton.setButtonListener {
            if (continueDescButton.isActive) {
                listener.onNext()
            }
        }

        sub.add(inputDesc.text.textChanges().subscribe {
            continueDescButton.isActive = it.isNotEmpty()
        })
    }

    override fun setupViewModel() {
    }
}