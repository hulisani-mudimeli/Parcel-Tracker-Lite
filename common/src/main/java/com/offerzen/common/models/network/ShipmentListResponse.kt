package com.offerzen.common.models.network

import kotlinx.serialization.Serializable

@Serializable
data class ShipmentListResponse(
    val items: List<ShipmentItem>,
    val total: Int,
)
