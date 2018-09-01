package com.tokodeveloper.reaction_time.di

import com.tokodeveloper.reaction_time.models.GameModel
import com.tokodeveloper.reaction_time.models.GameModelImpl
import com.tokodeveloper.reaction_time.services.GameService
import com.tokodeveloper.reaction_time.services.GameServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideMinimumTime(): Long = 200

    @Singleton
    @Provides
    fun provideGameModel(): GameModel = GameModelImpl(provideMinimumTime())

    @Singleton
    @Provides
    fun provideGameService(): GameService = GameServiceImpl(provideGameModel())
}