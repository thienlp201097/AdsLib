package trex.ads.alv_callback

import com.applovin.mediation.MaxAd
import com.applovin.mediation.ads.MaxInterstitialAd

interface InterstititialCallbackNew {
    fun onInterstitialReady(interstitialAd : MaxInterstitialAd)
    fun onInterstitialClosed()
    fun onInterstitialLoadFail(error:String)
    fun onInterstitialShowSucceed()
    fun onAdRevenuePaid(ad: MaxAd?)
}