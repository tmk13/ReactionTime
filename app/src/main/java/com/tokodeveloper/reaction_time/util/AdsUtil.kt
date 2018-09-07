package com.tokodeveloper.reaction_time.util

import android.content.Context
import android.os.Bundle
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.tokodeveloper.reaction_time.R


fun showPersonalizedAds(context: Context, adView: AdView) {
    val adRequest = getTestDevicesBuilder(context)
            .build()

    adView.loadAd(adRequest)
}

fun showPersonalizedAds(context: Context, interstitialAd: InterstitialAd) {
    val adRequest = getTestDevicesBuilder(context)
            .addNetworkExtrasBundle(AdMobAdapter::class.java, getNonPersonalizedAdsBundle())
            .build()

    interstitialAd.loadAd(adRequest)
}

fun showNonPersonalizedAds(context: Context, adView: AdView) {
    val adRequest = getTestDevicesBuilder(context)
            .addNetworkExtrasBundle(AdMobAdapter::class.java, getNonPersonalizedAdsBundle())
            .build()

    adView.loadAd(adRequest)
}

fun showNonPersonalizedAds(context: Context, interstitialAd: InterstitialAd) {
    val adRequest = getTestDevicesBuilder(context)
            .addNetworkExtrasBundle(AdMobAdapter::class.java, getNonPersonalizedAdsBundle())
            .build()

    interstitialAd.loadAd(adRequest)
}

private fun getTestDevicesBuilder(context: Context): AdRequest.Builder {
    return AdRequest.Builder()
            .addTestDevice(context.getString(R.string.cubot))
            .addTestDevice(context.getString(R.string.l65))
            .addTestDevice(context.getString(R.string.J5))
            .addTestDevice(context.getString(R.string.M4))
            .addTestDevice(context.getString(R.string.PHONEPAD))
            .addTestDevice(context.getString(R.string.G2Mini))
}

private fun getNonPersonalizedAdsBundle(): Bundle {
    val extras = Bundle()
    extras.putString("npa", "1")
    return extras
}