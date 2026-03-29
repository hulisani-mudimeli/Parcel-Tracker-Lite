package com.offerzen.common.models.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShipmentDetails(
    @SerialName("tracking_number") val trackingNumber: String?,
    val carrier: String?,
    val title: String?,
    val status: String?,
    val eta: String?,
    @SerialName("last_update") val lastUpdate: String?,
    val origin: ShipmentPlace?,
    val destination: ShipmentPlace?,
    val checkpoints: List<ShipmentCheckpoint>?,
)
