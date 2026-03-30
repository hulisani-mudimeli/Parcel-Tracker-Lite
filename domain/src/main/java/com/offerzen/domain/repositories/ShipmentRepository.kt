package com.offerzen.domain.repositories

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.models.network.ShipmentDetails
import com.offerzen.common.models.network.ShipmentItem
import com.offerzen.common.result.MyResult

interface ShipmentRepository {
    // Local
    suspend fun insertOrUpdateTrackedShipmentItem(shipment: ShipmentItemDb): MyResult<Unit>

    suspend fun bulkInsertOrUpdateTrackedShipmentItems(shipments: List<ShipmentItemDb>): MyResult<Unit>

    suspend fun fetchTrackedShipmentList(
        query: String? = null,
        favorite: Boolean? = null
    ): List<ShipmentItemDb>

    suspend fun markParcelAsFavorite(trackingNumber: String, favorite: Boolean): MyResult<Unit>

    suspend fun findTrackedShipmentByTrackingNumber(trackingNumber: String): ShipmentItemDb?


    // Network
    suspend fun fetchRemoteShipmentItem(trackingNumber: String): ShipmentItem?

    suspend fun fetchRemoteShipmentItems(trackingNumbers: List<String>): List<ShipmentItem>

    suspend fun fetchRemoteShipmentDetails(trackingNumber: String): ShipmentDetails?
}