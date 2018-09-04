package com.tokodeveloper.reaction_time

import android.app.Application
import androidx.fragment.app.Fragment
import com.tokodeveloper.reaction_time.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Inject

class App : Application(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initJodaTime()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    private fun initDagger() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

    }

    private fun initJodaTime() {
        JodaTimeAndroid.init(this)
    }
}