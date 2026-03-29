package com.offerzen.common.models.network

import kotlinx.serialization.Serializable

@Serializable
data class ShipmentPlace(
    val city: String?,
    val country: String?
)
