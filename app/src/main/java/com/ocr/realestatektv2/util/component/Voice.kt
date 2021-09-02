package com.ocr.realestatektv2.util.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import com.ocr.realestatektv2.R
import kotlinx.android.synthetic.main.component_voice.view.*

class Voice : FrameLayout {

    private var screenWidth : Int = 0

    private var isAnimated: Boolean = false
    private var steps: Int = 1
    private var currentStep: Int = 1
    private var stepWidth: Int = 0

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
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.Voice)

        isAnimated = attributes.getBoolean(R.styleable.Voice_animated, false)
        steps = attributes.getInt(R.styleable.Voice_steps, 1)

        inflate(context, R.layout.component_voice, this)
        val title = findViewById<TextView>(R.id.title)

        title.text = attributes.getString(R.styleable.Voice_title_text)

        if(isAnimated){
            getScreenWidth()
            initFirstStep()
        }

        attributes.recycle()
    }

    fun setText(text: String){
        title.text = text
    }

    fun setText(resId: Int){
        title.setText(resId)
    }

    fun getTitle(): TextView = title

    fun nextStep(){
        if (isAnimated && currentStep < steps)
            goToStep(++currentStep)
    }

    fun previousStep(){
        if (isAnimated && currentStep > 1)
            goToStep(--currentStep)
    }

    fun resetNumberOfSteps(steps: Int){
        if(isAnimated){
            this.steps = steps
            currentStep = 1
            initFirstStep()
        }
    }

    private fun goToStep(stepToReach: Int){
        val currentWidth = (voiceGradient.layoutParams as LayoutParams).width
        gradientAnimation(currentWidth, stepToReach * stepWidth)
    }

    private fun getScreenWidth(){
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
    }

    private fun calculateStepWidth(){
        stepWidth = (screenWidth / steps) - 10
    }

    private fun initFirstStep(){
        calculateStepWidth()
        // Set the view to width 0
        voiceGradient.layoutParams = LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        goToStep(currentStep)
    }

    private fun gradientAnimation(start: Int, end: Int){
        val valueAnimator = ValueAnimator.ofInt(start, end)
        valueAnimator.duration = 1000
        // set duration
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        // set interpolator and  updateListener to get the animated value
        valueAnimator.addUpdateListener {
            voiceGradient.layoutParams = LayoutParams(valueAnimator.animatedValue as Int, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        valueAnimator.start()
    }
}