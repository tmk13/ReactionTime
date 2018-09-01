package com.tokodeveloper.reaction_time.di

import com.tokodeveloper.reaction_time.GameFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeGameFragment(): GameFragment
}