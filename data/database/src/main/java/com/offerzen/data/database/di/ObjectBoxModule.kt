package com.offerzen.data.database.di

import android.content.Context
import com.offerzen.data.database.entities.tracked_shipment.objectbox.MyObjectBox
import com.offerzen.data.database.entities.tracked_shipment.objectbox.ShipmentItemOB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ObjectBoxModule {
    @Provides
    @Singleton
    fun provideBoxStore(@ApplicationContext context: Context): BoxStore {
        return MyObjectBox.builder()
            .androidContext(context)
            .build()
    }

    @Provides
    fun provideProductBox(boxStore: BoxStore): Box<ShipmentItemOB> {
        return boxStore.boxFor(ShipmentItemOB::class.java)
    }
}