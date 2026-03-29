package com.offerzen.common.models.database

data class ShipmentItemDb(
    var id: Long,
    var trackingId: String?,
    var trackingNumber: String?,
    var carrier: String?,
    var title: String?,
    var status: String?,
    var lastUpdate: String?,
    var favorite: Boolean = false
)
