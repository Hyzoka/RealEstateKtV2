package com.ocr.realestatektv2.ui.loading


import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.ocr.realestatektv2.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class EstateEditionTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreen::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
"android.permission.CAMERA",
"android.permission.WRITE_EXTERNAL_STORAGE")

    @Test
    fun estateEditionTest() {
        val appCompatImageView = onView(
allOf(withId(R.id.nav),
childAtPosition(
childAtPosition(
withId(R.id.container),
0),
0),
isDisplayed()))
        appCompatImageView.perform(click())

        val navigationMenuItemView = onView(
allOf(withId(R.id.nav_item_add),
childAtPosition(
allOf(withId(R.id.design_navigation_view),
childAtPosition(
withId(R.id.nav_view),
0)),
1),
isDisplayed()))
        navigationMenuItemView.perform(click())

        val appCompatEditText = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{956f9f1 VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741824}")),
0),
0),
isDisplayed()))
        appCompatEditText.perform(replaceText("Loft"), closeSoftKeyboard())

        val linearLayout = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.continueButton),
0)),
1),
isDisplayed()))
        linearLayout.perform(click())

        val appCompatEditText2 = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{a658357 VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741840}")),
0),
0),
isDisplayed()))
        appCompatEditText2.perform(replaceText("3"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.second_input), withText("androidx.appcompat.widget.AppCompatEditText{954fdf3 VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741841}")),
0),
0),
isDisplayed()))
        appCompatEditText3.perform(replaceText("6"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.three_input), withText("androidx.appcompat.widget.AppCompatEditText{b90f110 VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741842}")),
0),
0),
isDisplayed()))
        appCompatEditText4.perform(replaceText("1"), closeSoftKeyboard())

        val linearLayout2 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.continueButton),
0)),
1),
isDisplayed()))
        linearLayout2.perform(click())

        val appCompatEditText5 = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{428e912 VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741851}")),
0),
0),
isDisplayed()))
        appCompatEditText5.perform(replaceText("216000"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.second_input), withText("androidx.appcompat.widget.AppCompatEditText{b28845e VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741852}")),
0),
0),
isDisplayed()))
        appCompatEditText6.perform(replaceText("148"), closeSoftKeyboard())

        val linearLayout3 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.continueButton),
0)),
1),
isDisplayed()))
        linearLayout3.perform(click())

        val appCompatEditText7 = onView(
allOf(withId(R.id.inputTextView),
childAtPosition(
childAtPosition(
allOf(withId(R.id.inputDesc), withText("androidx.appcompat.widget.AppCompatEditText{fd1b87b VFED..CL. .F...... 53,21-1027,430 #7f090118 app:id/inputTextView aid=1073741860}")),
0),
0),
isDisplayed()))
        appCompatEditText7.perform(replaceText("tu as un peu plus de manière de travail "), closeSoftKeyboard())

        val linearLayout4 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.continueDescButton),
0)),
1),
isDisplayed()))
        linearLayout4.perform(click())

        val linearLayout5 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.openCam),
0)),
1),
isDisplayed()))
        linearLayout5.perform(click())

        val linearLayout6 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.openCam),
0)),
1),
isDisplayed()))
        linearLayout6.perform(click())

        val appCompatImageView2 = onView(
allOf(withId(R.id.clickme),
childAtPosition(
allOf(withId(R.id.bottomButtons),
childAtPosition(
withId(R.id.mainFrameLayout),
3)),
0),
isDisplayed()))
        appCompatImageView2.perform(click())

        val editText = onView(
allOf(childAtPosition(
allOf(withId(android.R.id.custom),
childAtPosition(
withClassName(`is`("android.widget.FrameLayout")),
0)),
0),
isDisplayed()))
        editText.perform(replaceText("living room"), closeSoftKeyboard())

        val appCompatButton = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton.perform(scrollTo(), click())

        val linearLayout7 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.openCam),
0)),
1),
isDisplayed()))
        linearLayout7.perform(click())

        val recyclerView = onView(
allOf(withId(R.id.instantRecyclerView),
childAtPosition(
withClassName(`is`("android.widget.FrameLayout")),
1)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(4, click()))

        val editText2 = onView(
allOf(childAtPosition(
allOf(withId(android.R.id.custom),
childAtPosition(
withClassName(`is`("android.widget.FrameLayout")),
0)),
0),
isDisplayed()))
        editText2.perform(replaceText("room"), closeSoftKeyboard())

        val appCompatButton2 = onView(
allOf(withId(android.R.id.button1), withText("OK"),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.ScrollView")),
0),
3)))
        appCompatButton2.perform(scrollTo(), click())

        val linearLayout8 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.nextFrag),
0)),
1),
isDisplayed()))
        linearLayout8.perform(click())

        val appCompatEditText8 = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{70394ad VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741891}")),
0),
0),
isDisplayed()))
        appCompatEditText8.perform(replaceText("29 "), closeSoftKeyboard())

        val appCompatEditText9 = onView(
allOf(withId(R.id.editText), withText("29 "),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{70394ad VFED..CL. .F.P.... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741891}")),
0),
0),
isDisplayed()))
        appCompatEditText9.perform(click())

        val appCompatEditText10 = onView(
allOf(withId(R.id.editText), withText("29 "),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{70394ad VFED..CL. .F....ID 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741891}")),
0),
0),
isDisplayed()))
        appCompatEditText10.perform(replaceText("29 rue voltaire, "))

        val appCompatEditText11 = onView(
allOf(withId(R.id.editText), withText("29 rue voltaire, "),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{70394ad VFED..CL. .F....ID 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741891}")),
0),
0),
isDisplayed()))
        appCompatEditText11.perform(closeSoftKeyboard())

        val appCompatEditText12 = onView(
allOf(withId(R.id.editText), withText("29 rue voltaire, "),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{70394ad VFED..CL. .F.P..ID 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741891}")),
0),
0),
isDisplayed()))
        appCompatEditText12.perform(click())

        val appCompatEditText13 = onView(
allOf(withId(R.id.editText), withText("29 rue voltaire, "),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{70394ad VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741891}")),
0),
0),
isDisplayed()))
        appCompatEditText13.perform(replaceText("29 rue voltaire, 59234 monchecourt "))

        val appCompatEditText14 = onView(
allOf(withId(R.id.editText), withText("29 rue voltaire, 59234 monchecourt "),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{70394ad VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741891}")),
0),
0),
isDisplayed()))
        appCompatEditText14.perform(closeSoftKeyboard())

        val appCompatCheckBox = onView(
allOf(withId(R.id.proxySchool),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatCheckBox.perform(click())

        val appCompatCheckBox2 = onView(
allOf(withId(R.id.proxySchool),
childAtPosition(
childAtPosition(
withClassName(`is`("android.widget.LinearLayout")),
1),
2),
isDisplayed()))
        appCompatCheckBox2.perform(click())

        val linearLayout9 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.continueButton),
0)),
1),
isDisplayed()))
        linearLayout9.perform(click())

        val appCompatRadioButton = onView(
allOf(withId(R.id.radioSold), withText("Sold"),
childAtPosition(
allOf(withId(R.id.radioEstate),
childAtPosition(
withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
0)),
1),
isDisplayed()))
        appCompatRadioButton.perform(click())

        val appCompatRadioButton2 = onView(
allOf(withId(R.id.radioSell), withText("Sell"),
childAtPosition(
allOf(withId(R.id.radioEstate),
childAtPosition(
withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
0)),
0),
isDisplayed()))
        appCompatRadioButton2.perform(click())

        val linearLayout10 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.buttonContinue),
0)),
1),
isDisplayed()))
        linearLayout10.perform(click())

        val appCompatEditText15 = onView(
allOf(withId(R.id.editText),
childAtPosition(
childAtPosition(
allOf(withId(R.id.first_input), withText("androidx.appcompat.widget.AppCompatEditText{95dda1a VFED..CL. .F...... 53,21-1027,200 #7f0900c5 app:id/editText aid=1073741910}")),
0),
0),
isDisplayed()))
        appCompatEditText15.perform(replaceText("MM Duvin"), closeSoftKeyboard())

        val linearLayout11 = onView(
allOf(withId(R.id.button),
childAtPosition(
allOf(withId(R.id.motion_base),
childAtPosition(
withId(R.id.continueButton),
0)),
1),
isDisplayed()))
        linearLayout11.perform(click())
        }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    }
