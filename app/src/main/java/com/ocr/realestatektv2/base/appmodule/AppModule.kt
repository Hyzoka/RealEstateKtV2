package com.ocr.realestatektv2.base.appmodule

import com.ocr.realestatektv2.addestate.AddEstateFlowViewModel
import com.ocr.realestatektv2.addestate.address.EstateAddressViewModel
import com.ocr.realestatektv2.addestate.agent.EstateAgentViewModel
import com.ocr.realestatektv2.addestate.date.EstateStatusViewModel
import com.ocr.realestatektv2.addestate.desc.EstateDescViewModel
import com.ocr.realestatektv2.addestate.picture.EstatePictureViewModel
import com.ocr.realestatektv2.addestate.price.EstatePriceSizeViewModel
import com.ocr.realestatektv2.addestate.type.EstateTypeViewModel
import com.ocr.realestatektv2.base.BaseViewModel
import com.ocr.realestatektv2.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

 val appModule = module {

        viewModel { BaseViewModel() }
        viewModel { EstateTypeViewModel() }
        viewModel { EstatePriceSizeViewModel() }
        viewModel { EstateDescViewModel() }
        viewModel { EstatePictureViewModel() }
        viewModel { EstateAddressViewModel() }
        viewModel { EstateStatusViewModel() }
        viewModel { EstateAgentViewModel() }
        viewModel { AddEstateFlowViewModel() }
        viewModel { DetailViewModel() }

}