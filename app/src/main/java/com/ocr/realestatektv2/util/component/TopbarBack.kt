package com.ocr.realestatektv2.util.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.util.Utils.gone
import com.ocr.realestatektv2.util.Utils.visible
import kotlinx.android.synthetic.main.component_topbar_back.view.*

class TopBarBack : ConstraintLayout {

    lateinit var backButton: ImageView
    lateinit var skipButton: LinearLayout
    lateinit var infoText: TextView

    private var textIsVisible: Boolean = true
        set(value) {
            field = value
            if(value){
                text.visible()
            }else{
                text.gone()
            }
        }

    private var backButtonIsVisible: Boolean = true
        set(value) {
            field = value
            if(value){
                backButton.visible()
            }else{
                backButton.gone()
            }
        }

    private var skipButtonIsVisible: Boolean = true
        set(value) {
            field = value
            if(value){
                skipButton.visible()
            }else{
                skipButton.gone()
            }
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    private fun init(context: Context, attributeSet: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.TopBarBack)

        inflate(context, R.layout.component_topbar_back, this)

        infoText = findViewById(R.id.text)
        backButton = findViewById(R.id.backButton)
        skipButton = findViewById(R.id.skipButton)

        infoText.text = attributes.getString(R.styleable.TopBarBack_info_text)

        backButton.setOnClickListener {
            (context as AppCompatActivity).onBackPressed()
        }

        attributes.recycle()

    }

    fun setInfoText(text: String){ infoText.text = text }
    fun setBackButtonListener(listener : ((View) -> Unit)?){ backButton.setOnClickListener(listener) }
    fun setSkipButtonListener(listener : ((View) -> Unit)?){ skipButton.setOnClickListener(listener) }
    fun setInfoTextVisibility(visible: Boolean){ textIsVisible = visible }
    fun setbackButtonVisibility(visible: Boolean){ backButtonIsVisible = visible }
    fun setSkipButtonVisibility(visible: Boolean){ skipButtonIsVisible = visible }
}

