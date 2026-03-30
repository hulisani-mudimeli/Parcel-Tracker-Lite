package com.offerzen.data.network.api.shipment.mock

import android.content.Context
import com.offerzen.common.helpers.json.DefaultJsonHelper
import com.offerzen.common.helpers.json.JsonHelper
import com.offerzen.common.models.network.ShipmentDetails
import com.offerzen.common.models.network.ShipmentDetailsResponse
import com.offerzen.common.models.network.ShipmentItem
import com.offerzen.common.models.network.ShipmentListResponse
import com.offerzen.data.network.R
import com.offerzen.data.network.api.shipment.ShipmentApiService
import kotlinx.serialization.json.Json

class MockShipmentApiService(
    private val context: Context,
    private val json: Json
): ShipmentApiService, JsonHelper by DefaultJsonHelper() {
    override suspend fun fetchShipmentItem(trackingNumber: String): ShipmentItem? {
        val jsonString = read(context, R.raw.shipment_list)
        val shipmentListResponse = json.decodeFromString(ShipmentListResponse.serializer(), jsonString)

        return shipmentListResponse.items.find { it.trackingNumber == trackingNumber }
    }

    override suspend fun fetchShipmentItems(trackingNumbers: List<String>): List<ShipmentItem> {
        val jsonString = read(context, R.raw.shipment_list)
        val shipmentListResponse = json.decodeFromString(ShipmentListResponse.serializer(), jsonString)

        return shipmentListResponse.items.filter { trackingNumbers.contains(it.trackingNumber) }
    }

    override suspend fun fetchShipmentDetails(trackingNumber: String): ShipmentDetails? {
        val jsonString = read(context, R.raw.shipment_details)
        val shipmentDetailsResponse = json.decodeFromString(ShipmentDetailsResponse.serializer(), jsonString)

        return shipmentDetailsResponse.details.find { it.trackingNumber == trackingNumber }
    }
}