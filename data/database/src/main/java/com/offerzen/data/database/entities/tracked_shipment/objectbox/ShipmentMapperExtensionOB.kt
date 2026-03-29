package com.offerzen.data.database.entities.tracked_shipment.objectbox

import com.offerzen.common.models.database.ShipmentItemDb


fun ShipmentItemOB.toDb(): ShipmentItemDb {
    return ShipmentItemDb(
        id = id,
        trackingId = trackingId,
        trackingNumber = trackingNumber,
        carrier = carrier,
        title = title,
        status = status,
        lastUpdate = lastUpdate,
        favorite = favorite
    )
}

fun ShipmentItemDb.toOB(): ShipmentItemOB {
    return ShipmentItemOB(
        id = id,
        trackingId = trackingId,
        trackingNumber = trackingNumber,
        carrier = carrier,
        title = title,
        status = status,
        lastUpdate = lastUpdate,
        favorite = favorite
    )
}