package com.offerzen.common.helpers

import com.offerzen.common.helpers.time_formatter.DefaultTimeFormatter
import com.offerzen.common.helpers.time_formatter.TimeFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelperModule {
    @Provides
    @Singleton
    fun provideTimeFormatter(): TimeFormatter = DefaultTimeFormatter()
}