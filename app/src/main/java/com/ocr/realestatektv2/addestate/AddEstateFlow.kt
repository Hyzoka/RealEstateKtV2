package com.ocr.realestatektv2.addestate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.fxn.pix.Pix
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.address.EstateAddressFragment
import com.ocr.realestatektv2.addestate.agent.EstateAgentFragment
import com.ocr.realestatektv2.addestate.date.EstateDateFragment
import com.ocr.realestatektv2.addestate.desc.EstateDescFragment
import com.ocr.realestatektv2.addestate.picture.EstatePcitureFragment
import com.ocr.realestatektv2.addestate.price.EstatePriceSizeFragment
import com.ocr.realestatektv2.addestate.rooms.EstateRoomsFragment
import com.ocr.realestatektv2.addestate.type.EstateTypeFragment
import com.ocr.realestatektv2.base.EstateBaseActivity
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.ui.home.MainActivity
import com.ocr.realestatektv2.util.DialogUtils.getDefaultPopUp
import com.ocr.realestatektv2.util.PIX_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_add_estate.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddEstateFlow : EstateBaseActivity<AddEstateFlowViewModel>(), ComponentListener {

    private var listPicture = arrayListOf<PictureEstate>()

    private var currentFragment: Fragment? = null

    private lateinit var typeFragment : EstateTypeFragment
    private lateinit var roomsFragment : EstateRoomsFragment
    private lateinit var priceSizeFragment: EstatePriceSizeFragment
    private lateinit var descFragment: EstateDescFragment
    private lateinit var pictureFragment: EstatePcitureFragment
    private lateinit var addressFragment: EstateAddressFragment
    private lateinit var dateFragment: EstateDateFragment
    private lateinit var agentFragment: EstateAgentFragment

    override fun viewModel(): AddEstateFlowViewModel {
        return getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)
        initFlow()
    }

    override fun setupViewModel() {}

    private fun initFlow() {
        initFragments()
        voice.resetNumberOfSteps(7)
        topbar.setBackButtonListener { onBackPressed() }
    }

    override fun onBackPressed() {
        when (currentFragment) {
            is EstateTypeFragment-> {
                finish()
            }
            is EstateRoomsFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = typeFragment
                switchTitles(getString(R.string.create_estate), getString(R.string.what_estate))
                topbar.setInfoTextVisibility(true)
            }
            is EstatePriceSizeFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = roomsFragment
                switchTitles(getString(R.string.create_estate), getString(R.string.how_much_rooms))
                topbar.setInfoTextVisibility(true)
            }
            is EstateDescFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = priceSizeFragment
                switchTitles(getString(R.string.detail_estate), getString(R.string.price_and_surface))
                topbar.setInfoTextVisibility(true)
            }
            is EstatePcitureFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = descFragment
                switchTitles(getString(R.string.detail_estate), getString(R.string.desc_estate))
                topbar.setInfoTextVisibility(true)
            }
            is EstateAddressFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = pictureFragment
                switchTitles(getString(R.string.detail_estate), getString(R.string.desc_estate))
                topbar.setInfoTextVisibility(true)
            }
            is EstateDateFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = addressFragment
                switchTitles(getString(R.string.detail_estate), getString(R.string.desc_estate))
                topbar.setInfoTextVisibility(true)
            }
            is EstateAgentFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = dateFragment
                switchTitles(getString(R.string.detail_estate), getString(R.string.desc_estate))
                topbar.setInfoTextVisibility(true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PIX_REQUEST_CODE) {

            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)

            if (returnValue?.first() != null) {
                getDefaultPopUp(this){ descPicture ->

                    returnValue.first()?.let { imagePath ->
                        //var test = PictureEstate(descPicture,imagePath)
                        //listPicture.add(test)
                        Log.i("PICTURE_RESULT", listPicture.toString())
                        Log.i("PICTURE_RESULT", listPicture.size.toString())
                    }
                }

            }
        }
    }

    private fun backFragment() {
        voice.previousStep()
        supportFragmentManager.popBackStack()
    }

    private fun initFragments() {
        typeFragment = EstateTypeFragment.newInstance(this)
        roomsFragment = EstateRoomsFragment.newInstance(this)
        priceSizeFragment = EstatePriceSizeFragment.newInstance(this)
        descFragment = EstateDescFragment.newInstance(this)
        dateFragment = EstateDateFragment.newInstance(this)
        pictureFragment = EstatePcitureFragment.newInstance(this)
        addressFragment = EstateAddressFragment.newInstance(this)
        agentFragment = EstateAgentFragment.newInstance(this)
        initFragmentsFlow()
    }

    private fun initFragmentsFlow() {
                switchTitles(getString(R.string.create_estate), getString(R.string.what_estate))
        topbar.setbackButtonVisibility(false)
        topbar.setSkipButtonVisibility(false)
                currentFragment = typeFragment
        showCurrentFragment()
    }

    private fun showCurrentFragment() {
        currentFragment?.let { fragment ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            )
            if (!fragment.isAdded) {
                transaction.add(R.id.fragment_content, fragment)
                transaction.addToBackStack("")
            } else {
                transaction.replace(R.id.fragment_content, fragment)
            }
            transaction.commit()
        }
    }

    private fun switchTitles(title: String, voiceText: String) {
        topbar.setInfoText(title)
        voice.setText(voiceText)
    }

    override fun onNext(data: Any?) {
        hideKeyboard()
        when (currentFragment) {
            is EstateTypeFragment -> {
                switchTitles(getString(R.string.create_estate), getString(R.string.how_much_rooms))
                topbar.setInfoTextVisibility(true)
                topbar.setbackButtonVisibility(true)
                currentFragment = roomsFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateRoomsFragment -> {
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                switchTitles(getString(R.string.detail_estate), getString(R.string.price_and_surface))
                currentFragment = priceSizeFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstatePriceSizeFragment -> {
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                switchTitles(getString(R.string.detail_estate), getString(R.string.desc_estate))
                currentFragment = descFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateDescFragment -> {
                switchTitles(getString(R.string.detail_estate), getString(R.string.take_picture))
                currentFragment = pictureFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstatePcitureFragment -> {
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                currentFragment = addressFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateAddressFragment -> {
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                currentFragment = dateFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateDateFragment -> {
                topbar.setbackButtonVisibility(true)
                currentFragment = agentFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateAgentFragment -> {
                lastStep()
            }
        }
        voice.nextStep()
    }

    private fun lastStep() {
            startActivity<MainActivity>()
        finish()
    }
}
