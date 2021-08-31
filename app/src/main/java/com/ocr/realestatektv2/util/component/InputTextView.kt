package com.ocr.realestatektv2.util.component

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import android.widget.LinearLayout
import com.ocr.realestatektv2.R
import kotlinx.android.synthetic.main.component_input_text_view.view.*

class InputTextView : LinearLayout {

    lateinit var text: EditText

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
        inflate(context, R.layout.component_input_text_view, this)
        text = inputTextView

        inputTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()){
                    val typeface: Typeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        resources.getFont(R.font.poppins_medium)
                    } else {
                        TODO("VERSION.SDK_INT < O")
                    }
                    inputTextView.typeface = typeface
                }
                else{
                    val typeface: Typeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        resources.getFont(R.font.poppins_bold)
                    } else {
                        TODO("VERSION.SDK_INT < O")
                    }
                    inputTextView.typeface = typeface

                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        text.setOnTouchListener(OnTouchListener { v, event ->
            if (text.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })


        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.InputTextView)
        text.hint = attributes.getString(R.styleable.InputTextView_hint_text_view)


        attributes.recycle()
    }

    fun getMessage() : String? {
        return inputTextView.text.toString().trim()
    }
}