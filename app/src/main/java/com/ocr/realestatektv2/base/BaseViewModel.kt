package com.ocr.realestatektv2.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ocr.realestatektv2.util.TOAST_NORMAL
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.reflect.KClass

open class BaseState(var loading: Boolean = false,
                     var succes: Boolean = false,
                     var errorMsg: String? = null)

open class BaseData<T : Any>(var data: T? = null,
                             var loading: Boolean = false,
                             var succes: Boolean = false,
                             var errorMsg: String? = null)

open class ToastData(
    var title: String?, var content: String?, var type: Int = TOAST_NORMAL,
    var duration: Int = 3000,
    var icon: Any? = null, var dismissable: Boolean = false, var callback: BaseActivity.Callback? = null
)

open class BaseViewModel : ViewModel(), KoinComponent {
    protected var cd: CompositeDisposable = CompositeDisposable()
    val activityToStart = MutableLiveData<Triple<KClass<*>, Bundle?, Int?>>()

    override fun onCleared() {
        super.onCleared()
        cd.dispose()
    }

}