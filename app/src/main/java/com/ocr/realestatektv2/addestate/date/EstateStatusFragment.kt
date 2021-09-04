package com.ocr.realestatektv2.addestate.date

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_date_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateStatusFragment : BaseComponentFragment<EstateStatusViewModel>() {

    private var status = ""
    private var statusEdit : String? = null
    private var dateEdit : String? = null

    companion object {
        fun newInstance(listener: ComponentListener) =
            EstateStatusFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_date_fragment
    override fun viewModel(): EstateStatusViewModel { return getViewModel() }

    override fun setupView() {
                if (statusEdit != null) {
                    if (statusEdit == "sell") {
                        radioEstate.check(R.id.radioSell)
                    } else{
                        radioEstate.check(R.id.radioSold)
                        inputDateSold.text.setText(dateEdit)
                        inputDateSold.visibility = View.VISIBLE
                    }
                    buttonContinue.isActive = true
                }

        radioSell.setOnClickListener {
            buttonContinue.isActive = true
            inputDateSold.visibility = View.GONE
            status = "sell"
        }

        radioSold.setOnClickListener {
            inputDateSold.visibility = View.VISIBLE
            buttonContinue.isActive = false
            status = "sold"
        }


        buttonContinue.setButtonListener {
            if (buttonContinue.isActive) {

                listener.onNext(arrayListOf(status, inputDateSold.text.text.toString()))
            }
        }

        sub.add(inputDateSold.text.textChanges().subscribe {
            buttonContinue.isActive = it.isNotEmpty()
        })
    }

    override fun setupViewModel() {
        inputDateSold.hintText("10/05/2021")
        inputDateSold.numberKeyboard = true
    }

    fun setStatusEdit(status : String, date : String?){
            statusEdit = status
            dateEdit = date
        }
}