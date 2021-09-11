package com.ocr.realestatektv2.ui.loading

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ocr.realestatektv2.R
import com.ocr.realestatektv2.ui.home.MainActivity
import com.ocr.realestatektv2.ui.tablet.MainTabletActivity
import org.jetbrains.anko.startActivity


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val tabletSize = resources.getBoolean(R.bool.isTablet)
        if (tabletSize) {
          Log.i("SCREEN", "TABLET")
            Handler().postDelayed({
                startActivity<MainTabletActivity>()
                finish()
            }, 1600)
        } else {
            Log.i("SCREEN", "PHONE")
            Handler().postDelayed({
                startActivity<MainActivity>()
                finish()
            }, 1600)
            // do something else
            //Start activity for phone
        }
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }
}
