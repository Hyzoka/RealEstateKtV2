package com.ocr.realestatektv2.addestate.address

import android.util.Log
import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_address_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateAddressFragment : BaseComponentFragment<EstateAddressViewModel>() {
    val result = StringBuilder()
    private var addressEdit : String? = null
    private var proxyAddressEdit : String? = null

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

        if (proxyAddressEdit != null){
            proxyWork.isChecked = proxyAddressEdit?.toLowerCase()?.contains("work")!!
            proxySchool.isChecked = proxyAddressEdit?.toLowerCase()?.contains("school")!!
            proxyStore.isChecked = proxyAddressEdit?.toLowerCase()?.contains("store")!!
            proxyParc.isChecked = proxyAddressEdit?.toLowerCase()?.contains("parc")!!
        }

        continueButton.setButtonListener {
            if (continueButton.isActive) {
                checkBox()
                listener.onNext(arrayListOf(first_input.text.text.toString(),result.toString()))  // result.toString() <-- add proxy list String, object need to update to arrayList
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
            result.append("work, ")
        }
        if (proxySchool.isChecked) {
            result.append("school, ")
        }
        if (proxyStore.isChecked) {
            result.append("store, ")
        }
        if (proxyParc.isChecked) {
            result.append("parc")
        }
    }

    fun setAddressEdit(address : String,proxyAddress : String?){  //, proxyAddress : ArrayList<String>
        addressEdit = address
        if (proxyAddress != null)
        proxyAddressEdit = proxyAddress
    }

    private fun teda(){
        val nsfw = listOf(
                "badword",
                "curseword",
                "ass"
        )
        val s1 = "This is the text that contains a badword"
        val s2 = "The grass is grean"
        val rx = Regex("\\b(?:${nsfw.joinToString(separator=",")})\\b")
        println(rx.containsMatchIn(s1)) // => true
        println(rx.containsMatchIn(s2)) // => false
    }
}