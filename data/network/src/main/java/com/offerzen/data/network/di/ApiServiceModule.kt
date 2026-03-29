package com.offerzen.data.network.di

import android.content.Context
import com.offerzen.data.network.api.shipment.ShipmentApiService
import com.offerzen.data.network.api.shipment.mock.MockShipmentApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun providesShipmentApiService(
        @ApplicationContext context: Context,
        json: Json
    ): ShipmentApiService {
        return MockShipmentApiService(context, json)
    }
}