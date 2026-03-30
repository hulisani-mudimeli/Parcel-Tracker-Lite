package com.offerzen.data.database.entities.tracked_shipment

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.result.MyResult

interface ShipmentStorageService {
    suspend fun insertOrUpdate(shipment: ShipmentItemDb): MyResult<Unit>

    suspend fun bulkInsertOrUpdateShipments(shipments: List<ShipmentItemDb>): MyResult<Unit>

    suspend fun fetchShipmentList(
        query: String? = null,
        favorite: Boolean? = null
    ): List<ShipmentItemDb>

    suspend fun markAsFavorite(trackingNumber: String, favorite: Boolean): MyResult<Unit>

    suspend fun findShipmentByTrackingNumber(trackingNumber: String): ShipmentItemDb?
}