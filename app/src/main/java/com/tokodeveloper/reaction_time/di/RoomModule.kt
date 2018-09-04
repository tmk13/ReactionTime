package com.tokodeveloper.reaction_time.di

import android.app.Application
import androidx.room.Room
import com.tokodeveloper.reaction_time.data.AppDatabase
import com.tokodeveloper.reaction_time.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application) = Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providesBestTimeDao(appDatabase: AppDatabase) = appDatabase.bestTimeDao()
}