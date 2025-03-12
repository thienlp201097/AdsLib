package com.lib.dktechads

import trex.ads.AppOpenManager
import trex.ads.adjust.AdjustUtils
import trex.ads.application.AdsApplication

class MyApplication : AdsApplication() {
    override fun onCreateApplication() {
        AdjustUtils.initAdjust(this,"",false)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN){
            AppOpenManager.getInstance().timeToBackground = System.currentTimeMillis()
        }
    }
}