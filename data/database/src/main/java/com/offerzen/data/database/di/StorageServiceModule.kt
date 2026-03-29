package com.offerzen.data.database.di

import com.offerzen.data.database.entities.tracked_shipment.ShipmentStorageService
import com.offerzen.data.database.entities.tracked_shipment.objectbox.DefaultShipmentStorageService
import com.offerzen.data.database.entities.tracked_shipment.objectbox.ShipmentItemOB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.objectbox.Box
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageServiceModule {
    @Provides
    @Singleton
    fun providesShipmentStorageService(
        productBox: Box<ShipmentItemOB>
    ): ShipmentStorageService {
        return DefaultShipmentStorageService(
            productBox
        )
    }
}