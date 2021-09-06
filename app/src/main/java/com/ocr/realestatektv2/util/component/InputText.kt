package com.ocr.realestatektv2.util.component

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ocr.realestatektv2.R
import kotlinx.android.synthetic.main.component_input_text.view.*

class InputText : LinearLayout {


    lateinit var text: EditText
    private var state: InputText.State = InputText.State.DEFAULT

    var numberKeyboard : Boolean = false
        set(value) {
            field = value
            if(value){
                text.inputType = InputType.TYPE_CLASS_NUMBER
            }else{
                text.inputType = InputType.TYPE_CLASS_TEXT
            }
        }

    var dateKeyboard : Boolean = false
        set(value) {
            field = value
            if(value){
                text.inputType = InputType.TYPE_DATETIME_VARIATION_NORMAL
            }else{
                text.inputType = InputType.TYPE_CLASS_TEXT
            }
        }


    enum class State{
        DEFAULT,
        FOCUSED,
        ERROR,
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private fun init(context: Context, attributeSet: AttributeSet?) {
        inflate(context, R.layout.component_input_text, this)
        text = editText

        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.InputText)
        text.hint = attributes.getString(R.styleable.InputText_hint)


        attributes.recycle()
    }

    fun hintText(hint : String){
        text.hint = hint
    }
}
