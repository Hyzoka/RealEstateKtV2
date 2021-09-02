package com.ocr.realestatektv2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ocr.realestatektv2.addestate.ComponentListener
import com.ocr.realestatektv2.ui.home.EstateViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseComponentFragment<V: BaseComponentViewModel>() : Fragment() {

    abstract fun layoutId(): Int
    abstract fun viewModel() : V
    lateinit var viewModel: V
    abstract fun setupView()
    abstract fun createView()
    abstract fun setupViewModel()

    lateinit var estateViewModel: EstateViewModel
    lateinit var mainView: View
    protected lateinit var listener: ComponentListener
    protected var inFlow: Boolean = false
    protected var sub: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle? ): View {
        mainView = inflater.inflate(layoutId(), container, false)
        viewModel = viewModel()
        initViewModel()
        createView()
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun initViewModel() { estateViewModel = ViewModelProvider(this).get(EstateViewModel::class.java) }


}