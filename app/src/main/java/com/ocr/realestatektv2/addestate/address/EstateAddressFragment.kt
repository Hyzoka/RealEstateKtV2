package com.ocr.realestatektv2.addestate.address

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_address_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateAddressFragment : BaseComponentFragment<EstateAddressViewModel>() {
    private var result : ArrayList<String> = arrayListOf()
    private var addressEdit : String? = null

    companion object {
        fun newInstance(listener: ComponentListener) =
            EstateAddressFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_address_fragment
    override fun viewModel(): EstateAddressViewModel { return getViewModel() }

    override fun setupView() {

        if (addressEdit != null){
            first_input.text.setText(addressEdit)
        }

        continueButton.setButtonListener {
            if (continueButton.isActive) {
                checkBox()
                listener.onNext(arrayListOf(first_input.text.text.toString()))  // result.toString() <-- add proxy list String, object need to update to arrayList
            }
        }

        sub.add(first_input.text.textChanges().subscribe {
            continueButton.isActive = it.isNotEmpty()
        })
    }

    override fun setupViewModel() {
        first_input.hintText("29 rue Voltaire, 59234 Monchecourt...")
    }

    private fun checkBox(){
        if (proxyWork.isChecked) {
            result.add("work")
        }
        if (proxySchool.isChecked) {
            result.add("school")
        }
        if (proxyStore.isChecked) {
            result.add("store")
        }
        if (proxyParc.isChecked) {
            result.add("parc")
        }
    }

    fun setAddressEdit(address : String){  //, proxyAddress : ArrayList<String>
        addressEdit = address
    }
}