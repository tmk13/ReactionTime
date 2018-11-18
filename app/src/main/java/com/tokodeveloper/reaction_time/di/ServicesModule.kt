package com.tokodeveloper.reaction_time.di

import com.tokodeveloper.reaction_time.services.GameService
import com.tokodeveloper.reaction_time.services.GameServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ServicesModule {

    @Provides
    @MinTime
    fun provideMinimumTime(): Long = 200

    @Provides
    fun provideGameService(gameServiceImpl: GameServiceImpl): GameService = gameServiceImpl
}