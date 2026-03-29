package com.offerzen.common.models.network

import kotlinx.serialization.Serializable

@Serializable
data class ShipmentDetailsResponse(
    val details: List<ShipmentDetails>
)
