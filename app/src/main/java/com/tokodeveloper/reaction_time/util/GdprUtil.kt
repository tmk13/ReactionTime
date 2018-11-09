package com.tokodeveloper.reaction_time.util

import android.content.Context
import com.tokodeveloper.reaction_time.R
import dae.gdprconsent.ConsentRequest

fun list(context: Context): ArrayList<ConsentRequest> {
    val list = ArrayList<ConsentRequest>()

    list.add(ConsentRequest(
            key = AGE_16,
            isRequired = true,
            added = "2018-09-06",
            title = context.getString(R.string.ask_for_age),
            category = context.getString(R.string.age),
            moreInformation = "https://gdpr-info.eu/art-8-gdpr/",
            what = context.getString(R.string.what_age),
            whyNeeded = context.getString(R.string.what_age)
    ))

    list.add(ConsentRequest(
            key = FIREBASE_STATISTICS,
            isRequired = false,
            added = "2018-09-06",
            title = context.getString(R.string.statistics),
            category = context.getString(R.string.analytics),
            moreInformation = "https://firebase.google.com/support/privacy/",
            what = context.getString(R.string.what_statistics),
            whyNeeded = context.getString(R.string.why_statistics)
    ))

    list.add(ConsentRequest(
            key = FIREBASE_CRASH,
            isRequired = false,
            added = "2018-09-06",
            title = context.getString(R.string.crash_reporting),
            category = context.getString(R.string.analytics),
            moreInformation = "https://firebase.google.com/support/privacy",
            what = context.getString(R.string.what_crash_reporting),
            whyNeeded = context.getString(R.string.why_crash_reporting)
    ))

    list.add(ConsentRequest(
            key = FIREBASE_MESSAGING,
            isRequired = false,
            added = "2018-09-06",
            title = context.getString(R.string.messaging),
            category = context.getString(R.string.messaging),
            what = context.getString(R.string.what_messaging),
            whyNeeded = context.getString(R.string.why_messaging),
            moreInformation = "https://firebase.google.com/support/privacy"
    ))

    return list
}