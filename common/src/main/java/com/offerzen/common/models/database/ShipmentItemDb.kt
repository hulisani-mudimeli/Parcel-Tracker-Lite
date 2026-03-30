package com.offerzen.common.models.database

import com.offerzen.common.enums.ShipmentStatus

data class ShipmentItemDb(
    var id: Long = 0,
    var trackingId: String? = null,
    var trackingNumber: String? = null,
    var carrier: String? = null,
    var title: String? = null,
    var status: ShipmentStatus = ShipmentStatus.Unknown,
    var lastUpdate: String? = null,
    var favorite: Boolean = false
)
