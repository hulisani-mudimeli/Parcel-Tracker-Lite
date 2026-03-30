package com.offerzen.data.repository.repositories

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.models.network.ShipmentDetails
import com.offerzen.common.models.network.ShipmentItem
import com.offerzen.common.result.MyResult
import com.offerzen.data.database.entities.tracked_shipment.ShipmentStorageService
import com.offerzen.data.network.api.shipment.ShipmentApiService
import com.offerzen.domain.repositories.ShipmentRepository

class DefaultShipmentRepository(
    private val shipmentStorageService: ShipmentStorageService,
    private val shipmentApiService: ShipmentApiService
): ShipmentRepository {
    override suspend fun insertOrUpdateTrackedShipmentItem(shipment: ShipmentItemDb): MyResult<Unit> {
        return shipmentStorageService.insertOrUpdate(shipment)
    }

    override suspend fun bulkInsertOrUpdateTrackedShipmentItems(shipments: List<ShipmentItemDb>): MyResult<Unit> {
        return shipmentStorageService.bulkInsertOrUpdateShipments(shipments)
    }

    override suspend fun fetchTrackedShipmentList(
        query: String?,
        favorite: Boolean?
    ): List<ShipmentItemDb> {
        return shipmentStorageService.fetchShipmentList(query, favorite)
    }

    override suspend fun markParcelAsFavorite(
        trackingNumber: String,
        favorite: Boolean
    ): MyResult<Unit> {
        return shipmentStorageService.markAsFavorite(trackingNumber, favorite)
    }

    override suspend fun findTrackedShipmentByTrackingNumber(trackingNumber: String): ShipmentItemDb? {
        return shipmentStorageService.findShipmentByTrackingNumber(trackingNumber)
    }

    override suspend fun fetchRemoteShipmentItem(trackingNumber: String): ShipmentItem? {
        return shipmentApiService.fetchShipmentItem(trackingNumber)
    }

    override suspend fun fetchRemoteShipmentItems(trackingNumbers: List<String>): List<ShipmentItem> {
        return shipmentApiService.fetchShipmentItems(trackingNumbers)
    }

    override suspend fun fetchRemoteShipmentDetails(trackingNumber: String): ShipmentDetails? {
        return shipmentApiService.fetchShipmentDetails(trackingNumber)
    }
}