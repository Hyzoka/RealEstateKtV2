package com.ocr.realestatektv2.ui.tablet

import com.jakewharton.rxbinding3.widget.textChanges
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.type.EstateTypeViewModel
import com.ocr.realestatektv2.base.BaseComponentFragment
import kotlinx.android.synthetic.main.estate_one_input_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class DetailFragmentTablet  : BaseComponentFragment<DetailTabletViewModel>() {


    override fun layoutId() = R.layout.detail_tablet_fragment
    override fun viewModel(): DetailTabletViewModel { return getViewModel() }

    override fun setupView() {


    }

    override fun setupViewModel() {
    }
}