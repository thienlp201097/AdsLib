package com.lib.dktechads

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import trex.ads.core.AdmobLib
import trex.ads.core.AppOpenManager
import trex.ads.R
import trex.ads.utils.Utils
import trex.ads.utils.admod.callback.MobileAdsListener
import com.lib.dktechads.databinding.ActivitySplashBinding
import com.lib.dktechads.utils.AdsManagerAdmod


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AdmobLib.initAdmob(this, isDebug = true, isEnableAds = true, object : MobileAdsListener {
            override fun onSuccess() {
                Log.d("==initAdmob==", "initAdmob onSuccess: ")
                AppOpenManager.getInstance()
                    .init(application, getString(R.string.test_ads_admob_app_open_new))
                AppOpenManager.getInstance()
                    .disableAppResumeWithActivity(SplashActivity::class.java)
                AppOpenManager.getInstance().setTestAds(false)

                AdsManagerAdmod.loadAndShowInterSplash(this@SplashActivity,AdsManagerAdmod.interholder,object : AdsManagerAdmod.AdListener{
                    override fun onAdClosed() {
                        Utils.getInstance().replaceActivity(this@SplashActivity, MainActivity::class.java)

                    }

                    override fun onFailed() {
                        Utils.getInstance().replaceActivity(this@SplashActivity, MainActivity::class.java)

                    }

                })
            }
        })
    }
}