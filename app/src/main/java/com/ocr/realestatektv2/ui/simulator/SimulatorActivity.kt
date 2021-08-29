package com.ocr.realestatektv2.ui.simulator

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.slider.Slider
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.base.BaseActivity
import com.ocr.realestatektv2.util.Utils

class SimulatorActivity : BaseActivity() {

    private lateinit var backButton: ImageView
    private lateinit var sliderDuration: Slider
    private lateinit var sliderAmount: Slider
    private lateinit var editContribution: EditText
    private lateinit var editInterest: EditText
    private lateinit var textMonthly: TextView

    private var amount = 250000
    private var apport = 0
    private var taux = 0.84
    private var duration = 15

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simulator_activity)

        textMonthly = findViewById(R.id.monthly_payment)
        sliderDuration = findViewById(R.id.slider_duration_Loan)
        sliderAmount = findViewById(R.id.slider_amount_Loan)
        editContribution = findViewById(R.id.contribution_edit)
        editInterest = findViewById(R.id.interest_edit)

        setupSlider()
        setupListener()
        textWatcher()
    }

    private fun setupListener() {
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

    }

    private fun setupSlider() {

        sliderDuration.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being started
            }

            override fun onStopTrackingTouch(slider: Slider) {
            }
        })

        sliderDuration.addOnChangeListener { slider, value, fromUser ->
            duration = value.toInt()
            textMonthly.text = Utils.loanSimulator(amount, duration, taux, apport).toString()
        }

        sliderAmount.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being started
            }

            override fun onStopTrackingTouch(slider: Slider) {
            }
        })

        sliderAmount.addOnChangeListener { slider, value, fromUser ->
            amount = value.toInt()
            textMonthly.text = Utils.loanSimulator(amount, duration, taux, apport).toString()
        }
    }

    private fun textWatcher(){
        editContribution.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textMonthly.text = Utils.loanSimulator(amount, duration, taux, apport).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                apport = Integer.parseInt(s.toString())
            }
        })

        editInterest.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textMonthly.text = Utils.loanSimulator(amount, duration, taux, apport).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                taux = s.toString().toDouble()


            }
        })
    }
}
