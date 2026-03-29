package com.offerzen.common.models.network

import kotlinx.serialization.Serializable

@Serializable
data class ShipmentCheckpoint(
    val time: String?,
    val location: String?,
    val status: String?,
    val message: String?
)
