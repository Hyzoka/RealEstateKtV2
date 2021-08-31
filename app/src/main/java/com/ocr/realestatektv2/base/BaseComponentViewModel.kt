package com.ocr.realestatektv2.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ocr.realestatektv2.model.Estate
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseComponentViewModel : ViewModel(), KoinComponent {

    protected var cd: CompositeDisposable = CompositeDisposable()
    val estates: MutableLiveData<Estate> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        cd.dispose()
    }

}