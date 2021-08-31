package com.ocr.realestatektv2.util.component

import android.content.Context
import android.text.SpannableString
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.util.Utils.gone
import com.ocr.realestatektv2.util.Utils.invisible
import com.ocr.realestatektv2.util.Utils.setMargins
import com.ocr.realestatektv2.util.Utils.visible

class PrimaryButton : ConstraintLayout {

    var isActive : Boolean = false
        set(value) {
            field = value
            if(value){
                button.background = ContextCompat.getDrawable(context, R.drawable.background_primary_button)
                button.setOnClickListener(listener)
            }else{
                button.background = ContextCompat.getDrawable(context, R.drawable.background_prirmary_button_inactive)
                button.setOnClickListener(null)
            }
        }

    var isAlert : Boolean = false
        set(value) {
            field = value
            if(value){
                button.background = ContextCompat.getDrawable(context, R.drawable.background_primary_button_alert)
            }
        }

    var hasInfoText : Boolean = false
        set(value) {
            field = value
            if(value){
                infoText.visible()
                button.setMargins(scale,0,24,0, 24)

            }else{
                infoText.gone()
                button.setMargins(scale,0,24,0, 24)
            }
        }

    var isAnimate : Boolean = false
        set(value) {
            field = value
        }

    private lateinit var button : LinearLayout
    private lateinit var textButton : TextView
    private lateinit var infoText : TextView
    private var listener : ((View) -> Unit)? = null
    private lateinit var lottie : LottieAnimationView
    private lateinit var lottieInfini : LottieAnimationView
    private lateinit var motionLayout : MotionLayout

    private var scale: Float = 0f

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

    private fun init(context: Context, attributeSet: AttributeSet?){
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.PrimaryButton)

        inflate(context, R.layout.component_primary_button, this)

        scale = context.resources.displayMetrics.density

        button = findViewById(R.id.button)
        textButton = findViewById(R.id.buttonText)
        infoText = findViewById(R.id.stepInfo)
        lottie = findViewById(R.id.animation)
        lottieInfini = findViewById(R.id.animationInfini)
        motionLayout = findViewById(R.id.motion_base)

        textButton.text = attributes.getString(R.styleable.PrimaryButton_button_text)
        infoText.text = attributes.getString(R.styleable.PrimaryButton_step_title)
        isActive = attributes.getBoolean(R.styleable.PrimaryButton_android_enabled, false)

        if (!infoText.text.isNullOrBlank()){
            hasInfoText = true
        }
        if (isAnimate){
            motionLayout.transitionToEnd()
        }
        attributes.recycle()
    }

    fun setButtonListener(listener : ((View) -> Unit)?){
        this.listener = listener!!
        button.setOnClickListener(listener)
    }
    fun setButtonText(text: String){ textButton.text = text }
    fun setButtonText(text: SpannableString){ textButton.text = text }
    fun setInfoText(text: String){ infoText.text = text }

}