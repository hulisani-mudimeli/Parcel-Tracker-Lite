package com.offerzen.common.models.mappers

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.models.network.ShipmentItem

fun ShipmentItemDb.toNetwork(): ShipmentItem {
    return ShipmentItem(
        id = trackingId,
        trackingNumber = trackingNumber,
        carrier = carrier,
        title = title,
        status = status,
        lastUpdate = lastUpdate
    )
}

fun ShipmentItem.toDb(): ShipmentItemDb {
    return ShipmentItemDb(
        id = 0,
        trackingId = id,
        trackingNumber = trackingNumber,
        carrier = carrier,
        title = title,
        status = status,
        lastUpdate = lastUpdate
    )
}