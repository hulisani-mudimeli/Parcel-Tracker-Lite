package com.offerzen.data.database.entities.tracked_shipment

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.result.Result

interface ShipmentStorageService {
    suspend fun insertOrUpdate(shipment: ShipmentItemDb): Result<Unit>

    suspend fun bulkInsertOrUpdateShipments(shipments: List<ShipmentItemDb>): Result<Unit>

    suspend fun fetchShipmentList(
        query: String? = null,
        favorite: Boolean? = null
    ): List<ShipmentItemDb>

    suspend fun markAsFavorite(trackingNumber: String, favorite: Boolean): Result<Unit>
}