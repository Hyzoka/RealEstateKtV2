package com.ocr.realestatektv2.addestate.agent

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_one_input_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class EstateAgentFragment : BaseComponentFragment<EstateAgentViewModel>() {

    private var agentEdit : String? = null
    companion object {
        fun newInstance(listener: ComponentListener) =
            EstateAgentFragment().apply { this.listener = listener }
    }

    override fun layoutId() = R.layout.estate_one_input_fragment
    override fun viewModel(): EstateAgentViewModel { return getViewModel() }

    override fun setupView() {

        if (agentEdit != null){
                    first_input.text.setText(agentEdit)
        }


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
        first_input.hintText("Mm Valiant")
    }

    fun setAgentEdit(agent : String){
        agentEdit = agent
    }
}