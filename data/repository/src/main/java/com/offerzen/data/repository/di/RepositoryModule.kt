package com.offerzen.data.repository.di

import com.offerzen.data.database.entities.tracked_shipment.ShipmentStorageService
import com.offerzen.data.network.api.shipment.ShipmentApiService
import com.offerzen.data.repository.repositories.DefaultShipmentRepository
import com.offerzen.domain.repositories.ShipmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesShipmentRepository(
        shipmentStorageService: ShipmentStorageService,
        shipmentApiService: ShipmentApiService
    ): ShipmentRepository {
        return DefaultShipmentRepository(
            shipmentStorageService,
            shipmentApiService
        )
    }
}