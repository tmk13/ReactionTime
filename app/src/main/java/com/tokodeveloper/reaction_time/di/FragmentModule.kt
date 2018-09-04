package com.tokodeveloper.reaction_time.di

import com.tokodeveloper.reaction_time.GameFragment
import com.tokodeveloper.reaction_time.HistoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeGameFragment(): GameFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHistoryFragment(): HistoryFragment
}