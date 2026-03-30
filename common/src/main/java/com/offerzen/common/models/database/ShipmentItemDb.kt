package com.offerzen.common.models.database

import com.offerzen.common.enums.ShipmentStatus

data class ShipmentItemDb(
    var id: Long,
    var trackingId: String?,
    var trackingNumber: String?,
    var carrier: String?,
    var title: String?,
    var status: ShipmentStatus = ShipmentStatus.Unknown,
    var lastUpdate: String?,
    var favorite: Boolean = false
)
