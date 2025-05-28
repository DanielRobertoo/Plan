package com.example.plan.di

import android.content.Context
import com.example.base.datasore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }


}