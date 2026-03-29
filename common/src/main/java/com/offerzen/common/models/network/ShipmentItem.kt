package com.offerzen.common.models.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShipmentItem(
    val id: String?,
    @SerialName("tracking_number") val trackingNumber: String?,
    val carrier: String?,
    val title: String?,
    val status: String?,
    @SerialName("last_update") val lastUpdate: String?,
)
