package com.lib.dktechads.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import trex.ads.core.AdmobLib
import trex.ads.core.AdmobLib.AdsNativeCallBackAdmod
import trex.ads.core.AppOpenManager
import trex.ads.utils.GoogleENative
import trex.ads.utils.Utils
import trex.ads.utils.admod.InterHolderAdmob
import trex.ads.utils.admod.NativeHolderAdmob
import trex.ads.utils.admod.callback.AdCallBackInterLoad
import trex.ads.utils.admod.callback.AdsInterCallBack
import trex.ads.utils.admod.callback.NativeAdmobCallback
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.lib.dktechads.R

object AdsManagerAdmod {
    var nativeHolder = NativeHolderAdmob("ca-app-pub-3940256099942544/2247696110")
    var nativeHolderFull = NativeHolderAdmob("ca-app-pub-3940256099942544/7342230711")
    var interholder = InterHolderAdmob("ca-app-pub-3940256099942544/1033173712")

    fun loadInter(context: Context, interHolder: InterHolderAdmob) {
        AdmobLib.loadAndGetAdInterstitial(context,interHolder,
            object :
                AdCallBackInterLoad {
                override fun onAdClosed() {
                    Utils.getInstance().showMessenger(context, "onAdClosed")
                }

                override fun onEventClickAdClosed() {
                    Utils.getInstance().showMessenger(context, "onEventClickAdClosed")
                }

                override fun onAdShowed() {
                    Utils.getInstance().showMessenger(context, "onAdShowed")
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd, isLoad: Boolean) {
                    interholder.inter = interstitialAd
                    interHolder.check = isLoad
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onAdFail(message: String?) {
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {

                }
            }
        )
    }


    fun showInter(
        context: Context,
        interHolder: InterHolderAdmob,
        adListener: AdListener,
        enableLoadingDialog: Boolean
    ) {
        AdmobLib.showAdInterstitialWithCallbackNotLoadNew(
            context as Activity,interHolder,10000, object :
                AdsInterCallBack {
                override fun onAdLoaded() {
                    Utils.getInstance().showMessenger(context, "onAdLoaded")
                }

                override fun onStartAction() {
                    adListener.onAdClosed()
                }

                override fun onAdFail(error: String?) {
                    interHolder.inter = null
                    loadInter(context,interHolder)
                    adListener.onFailed()
                    Utils.getInstance().showMessenger(context, "onAdFail")
                }

                override fun onClickAds() {

                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {

                }

                override fun onEventClickAdClosed() {
                    interHolder.inter = null
                    loadInter(context,interHolder)
//                    adListener.onAdClosed()
                    Utils.getInstance().showMessenger(context, "onEventClickAdClosed")
                }

                override fun onAdShowed() {
                    Utils.getInstance().showMessenger(context, "onAdShowed")
                }
            }, enableLoadingDialog)
    }

    fun loadAdsNativeNew(context: Context, holder: NativeHolderAdmob) {
        AdmobLib.loadAndGetNativeAds(
            context,
            holder,
            object : NativeAdmobCallback {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
                }

                override fun onNativeAdLoaded() {
                }

                override fun onAdFail(error: String?) {
                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {

                }
            })
    }

    fun showNative(activity: Activity, viewGroup: ViewGroup, holder: NativeHolderAdmob) {
        if (!AdmobLib.isNetworkConnected(activity)) {
            viewGroup.visibility = View.GONE
            return
        }
        AdmobLib.showNativeAdsWithLayout(activity, holder, viewGroup, R.layout.ad_unified_medium, GoogleENative.UNIFIED_BANNER, object : AdmobLib.AdsNativeCallBackAdmod {
            override fun NativeLoaded() {
                Utils.getInstance().showMessenger(activity, "onNativeShow")
            }

            override fun NativeFailed(massage: String) {
                Utils.getInstance().showMessenger(activity, "onAdsFailed")
            }

            override fun onPaid(adValue: AdValue?, adUnitAds: String?) {

            }
        })
    }

    fun showAdsNativeFullScreen(activity: Activity, nativeHolder: NativeHolderAdmob,viewGroup: ViewGroup){
        AdmobLib.showNativeFullScreenAdsWithLayout(activity,nativeHolder,viewGroup,
            R.layout.ad_native_fullscreen,object :
                AdsNativeCallBackAdmod {
                override fun NativeLoaded() {
                    Log.d("==full==", "NativeLoaded: ")
                }

                override fun NativeFailed(massage: String) {
                    Log.d("==full==", "NativeFailed: $massage")
                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {

                }

            })
    }

    fun loadAndShowInterSplash(
        context: Context,
        interHolder: InterHolderAdmob,
        callback: AdListener
    ) {

        AppOpenManager.getInstance().isAppResumeEnabled = true
        AdmobLib.loadAndShowAdInterstitial(
            context as AppCompatActivity,
            interHolder,
            object : AdsInterCallBack {
                override fun onStartAction() {

                }

                override fun onEventClickAdClosed() {
                    callback.onAdClosed()
                }

                override fun onAdShowed() {
                    AppOpenManager.getInstance().isAppResumeEnabled = false
                    Handler(Looper.getMainLooper()).postDelayed({
                        try {
                            AdmobLib.dismissAdDialog()
                        } catch (_: Exception) {

                        }
                    }, 800)
                }

                override fun onAdLoaded() {

                }

                override fun onAdFail(p0: String?) {
                    callback.onFailed()
                }

                override fun onClickAds() {

                }

                override fun onPaid(p0: AdValue?, p1: String?) {
                }
            },
            false
        )
    }


    interface AdListener {
        fun onAdClosed()
        fun onFailed()
    }
}
