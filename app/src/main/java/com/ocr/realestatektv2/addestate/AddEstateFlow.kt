package com.ocr.realestatektv2.addestate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.addestate.address.EstateAddressFragment
import com.ocr.realestatektv2.addestate.agent.EstateAgentFragment
import com.ocr.realestatektv2.addestate.date.EstateStatusFragment
import com.ocr.realestatektv2.addestate.desc.EstateDescFragment
import com.ocr.realestatektv2.addestate.picture.EstatePictureFragment
import com.ocr.realestatektv2.addestate.price.EstatePriceSizeFragment
import com.ocr.realestatektv2.addestate.rooms.EstateRoomsFragment
import com.ocr.realestatektv2.addestate.type.EstateTypeFragment
import com.ocr.realestatektv2.base.EstateBaseActivity
import com.ocr.realestatektv2.model.Estate
import com.ocr.realestatektv2.model.PictureEstate
import com.ocr.realestatektv2.ui.detail.DetailActivity
import com.ocr.realestatektv2.ui.home.MainActivity
import com.ocr.realestatektv2.util.*
import kotlinx.android.synthetic.main.activity_add_estate.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class AddEstateFlow : EstateBaseActivity<AddEstateFlowViewModel>(), ComponentListener {

    private var currentFragment: Fragment? = null

    private lateinit var typeFragment : EstateTypeFragment
    private lateinit var roomsFragment : EstateRoomsFragment
    private lateinit var priceSizeFragment: EstatePriceSizeFragment
    private lateinit var descFragment: EstateDescFragment
    private lateinit var pictureFragment: EstatePictureFragment
    private lateinit var addressFragment: EstateAddressFragment
    private lateinit var statusFragment: EstateStatusFragment
    private lateinit var agentFragment: EstateAgentFragment

    // for insert data in room
    private var id = 0
    private var type = ""
    private var nbrRooms = ""
    private var  nbrBedRooms= ""
    private var nbrBathRooms = ""
    private var price = ""
    private var surface = ""
    private var desc = ""
    private var pictureArray = arrayListOf<PictureEstate>()
    private var addresse = ""
    private var proxyAddress = ""
    //private var proxyAddress = arrayListOf<String>()
    private var status = ""
    private var dateSell : String? = null
    private var agent = ""

    //edit
    private lateinit var editEstate : Estate
    private var idEdit = 0
    private var fromDetail = false
    private var dateCreate = ""


    override fun viewModel(): AddEstateFlowViewModel {
        return getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)
        getEditData()
        initFlow()
        //create id with size estate list from mainActivity
        id = intent.getIntExtra(ID_ESTATE, 50)
    }

    override fun setupViewModel() {}

    private fun initFlow() {
        initFragments()
        voice.resetNumberOfSteps(8)
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
            is EstatePictureFragment -> {
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
                switchTitles(getString(R.string.detail_estate), getString(R.string.take_picture))
                topbar.setInfoTextVisibility(true)
            }
            is EstateStatusFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = addressFragment
                switchTitles(getString(R.string.detail_estate), getString(R.string.what_estate_address))
                topbar.setInfoTextVisibility(true)
            }
            is EstateAgentFragment -> {
                backFragment()
                topbar.setbackButtonVisibility(true)
                currentFragment = statusFragment
                switchTitles(getString(R.string.detail_estate), getString(R.string.is_on_sell))
                topbar.setInfoTextVisibility(true)
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
        statusFragment = EstateStatusFragment.newInstance(this)
        pictureFragment = EstatePictureFragment.newInstance(this)
        addressFragment = EstateAddressFragment.newInstance(this)
        agentFragment = EstateAgentFragment.newInstance(this)
        initFragmentsFlow()
    }

    private fun initFragmentsFlow() {
        if (fromDetail){
            switchTitles(getString(R.string.edit_estate), getString(R.string.what_estate))
            topbar.setbackButtonVisibility(false)
            topbar.setSkipButtonVisibility(true)
            currentFragment = typeFragment
        }
        else
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
                data.let { estateType ->
                    type = estateType.toString()
                    Log.i("DATA_GET",type)
                }
                switchTitles(getString(R.string.create_estate), getString(R.string.how_much_rooms))
                topbar.setInfoTextVisibility(true)
                topbar.setbackButtonVisibility(true)
                currentFragment = roomsFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateRoomsFragment -> {
                data.let { listRooms ->
                    listRooms as ArrayList<String>
                    nbrRooms = listRooms[0]
                    nbrBedRooms = listRooms[1]
                    nbrBathRooms = listRooms[2]
                    Log.i("DATA_GET",nbrRooms + nbrBedRooms + nbrBathRooms)
                }
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                switchTitles(getString(R.string.detail_estate), getString(R.string.price_and_surface))
                currentFragment = priceSizeFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstatePriceSizeFragment -> {
                data.let { listPriceSize ->
                    listPriceSize as ArrayList<String>
                    price = listPriceSize[0]
                    surface = listPriceSize[1]
                    Log.i("DATA_GET",price + surface)
                }
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                switchTitles(getString(R.string.detail_estate), getString(R.string.desc_estate))
                currentFragment = descFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateDescFragment -> {
                data.let { descript ->
                    desc = descript.toString()
                    Log.i("DATA_GET",desc)
                }
                switchTitles(getString(R.string.detail_estate), getString(R.string.take_picture))
                currentFragment = pictureFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstatePictureFragment -> {
                data.let { pictureList ->
                    pictureList as ArrayList<PictureEstate>
                    pictureArray = pictureList
                    Log.i("DATA_GET",pictureArray.toString())
                }
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                switchTitles(getString(R.string.detail_estate), getString(R.string.what_estate_address))
                currentFragment = addressFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateAddressFragment -> {
                data.let { addressList ->
                    addressList as ArrayList<String>
                    addresse = addressList[0]
                    proxyAddress = addressList[1]

                    Log.i("DATA_GET",addresse + proxyAddress)
                }
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                switchTitles(getString(R.string.detail_estate), getString(R.string.is_on_sell))
                currentFragment = statusFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateStatusFragment -> {
                data.let { statusList ->
                    statusList as ArrayList<String>
                    status = statusList[0]
                    dateSell = statusList[1]
                    Log.i("DATA_GET",status + dateSell)
                }
                topbar.setbackButtonVisibility(true)
                topbar.setbackButtonVisibility(true)
                switchTitles(getString(R.string.detail_estate), getString(R.string.agent_add_estate))
                currentFragment = agentFragment
                currentFocus?.clearFocus()
                showCurrentFragment()
            }
            is EstateAgentFragment -> {
                data.let { agentFrag ->
                    agent = agentFrag.toString()
                    Log.i("DATA_GET",agent)
                }
                lastStep()
            }
        }
        voice.nextStep()
    }

    private fun lastStep() {
        if (!fromDetail){
            createEstate()
            startActivity<MainActivity>(SHOW_POP to true)
        }
        else{
            editEstate()
            startActivity<MainActivity>(SHOW_POP to false)
        }

            finish()
    }

    private fun getLocalDateNow(): String{
        return  LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_US_FORMAT))
    }

    private fun createEstate(){
        val estateObject = Estate(id+1 ,type,surface,nbrRooms,nbrBedRooms,nbrBathRooms,desc,pictureArray,addresse,proxyAddress,status,getLocalDateNow(),dateSell,agent,price)
        estateViewModel.insert(estateObject)
           }

    private fun editEstate(){
        val estateObject = Estate(idEdit ,type,surface,nbrRooms,nbrBedRooms,nbrBathRooms,desc,pictureArray,addresse,proxyAddress,status,dateCreate,dateSell,agent,price)
        estateViewModel.update(estateObject)
    }

    private fun getEditData(){
        //for edit estate
        fromDetail = intent.getBooleanExtra(FROM_DETAIL,false)
        if (fromDetail) {
            val data = intent.getIntExtra(EDIT_ESTATE, 36)
            estateViewModel.estateList.observe(this,

                Observer { estateList: List<Estate> ->
                    for (estate in estateList) {
                        if (estate.id == data) {
                            editEstate = estate
                            idEdit = estate.id
                            dateCreate = estate.createDate
                            Log.i("EDIT_DATA",editEstate.addresse)
                            Log.i("EDIT_DATA",editEstate.typeEstate)

                            typeFragment.setTypeEdit(estate.typeEstate)
                            roomsFragment.setRoomsEdit(arrayListOf(estate.nbrRoom,estate.nbrBedRoom,estate.nbrBathRoom))
                            priceSizeFragment.setPriceSizeEdit(arrayListOf(estate.price,estate.surface))
                            descFragment.setDescEdit(estate.description)
                            pictureFragment.setPictureEdit(estate.picture)
                            addressFragment.setAddressEdit(estate.addresse)
                            statusFragment.setStatusEdit(estate.status,estate.soldDate)
                            agentFragment.setAgentEdit(estate.manager)
                        }
                    }
                })
        }
    }
}
