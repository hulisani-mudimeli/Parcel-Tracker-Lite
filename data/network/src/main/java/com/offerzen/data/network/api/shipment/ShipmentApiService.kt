package com.offerzen.data.network.api.shipment

import com.offerzen.common.models.network.ShipmentDetails
import com.offerzen.common.models.network.ShipmentItem

interface ShipmentApiService {
    suspend fun fetchShipmentItem(trackingNumber: String): ShipmentItem?

    suspend fun fetchShipmentItems(trackingNumbers: List<String>): List<ShipmentItem>

    suspend fun fetchShipmentDetails(trackingNumber: String): ShipmentDetails?
}