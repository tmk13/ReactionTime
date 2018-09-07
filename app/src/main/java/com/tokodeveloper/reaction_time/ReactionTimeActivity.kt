package com.tokodeveloper.reaction_time

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.tokodeveloper.reaction_time.data.BestTime
import com.tokodeveloper.reaction_time.util.*
import dae.gdprconsent.ConsentHelper
import dae.gdprconsent.Constants
import kotlinx.android.synthetic.main.activity_reaction_time.*


class ReactionTimeActivity : AppCompatActivity(), HistoryFragment.OnListFragmentInteractionListener {

    private lateinit var interstitialAd: InterstitialAd

    companion object {
        val CHANGE_CONSENTS = "changeConsents"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction_time)

        val navController = Navigation.findNavController(this, R.id.reaction_nav_fragment)

        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)

        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id))

        val allowsPersonalizedAds = ConsentHelper.hasConsent(ADMOB_PERSONALIZED)

        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id)
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                if (allowsPersonalizedAds) {
                    showPersonalizedAds(this@ReactionTimeActivity, interstitialAd)
                } else {
                    showNonPersonalizedAds(this@ReactionTimeActivity, interstitialAd)
                }
            }
        }

        if (allowsPersonalizedAds) {
            showPersonalizedAds(this, interstitialAd)
        } else {
            showNonPersonalizedAds(this, interstitialAd)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(null, Navigation.findNavController(this, R.id.reaction_nav_fragment))
    }

    override fun onBackPressed() {
        if (Navigation.findNavController(this, R.id.reaction_nav_fragment)
                        .currentDestination?.id == R.id.gameFragment) {
            if (interstitialAd.isLoaded) {
                interstitialAd.show()
            }
        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_CONSENT) {
            if (resultCode == Activity.RESULT_OK) {
                // User passed through the consent system completely

                // Did consent change?
                val consentChanged = data?.extras?.getBoolean(Constants.KEY_CONSENT_CHANGED)
                        ?: false
                if (consentChanged) {
                    // If consent was changed, restart the App. This lets the Application class initialize services such as Analytics
                    val intent = Intent(this, ReactionTimeActivity::class.java)
                    val pending = PendingIntent.getActivity(this, RC_RESTART, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                    val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pending)
                    finish()
                    System.exit(0)
                }
            } else {
                // User closed the consent system without progressing through it
                finish()
            }
        }
    }

    override fun onListFragmentInteraction(bestTime: BestTime?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
