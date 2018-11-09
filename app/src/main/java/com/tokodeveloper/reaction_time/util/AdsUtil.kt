package com.tokodeveloper.reaction_time.util

import android.os.Bundle

private fun getNonPersonalizedAdsBundle(): Bundle {
    val extras = Bundle()
    extras.putString("npa", "1")
    return extras
}