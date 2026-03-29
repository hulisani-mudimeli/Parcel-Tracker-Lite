package com.offerzen.data.database.entities.tracked_shipment.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class ShipmentItemOB (
    @Id var id: Long = 0,
    @Unique var trackingId: String? = null,
    @Unique var trackingNumber: String? = null,
    var carrier: String? = null,
    var title: String? = null,
    var status: String? = null,
    var lastUpdate: String? = null,
    var favorite: Boolean = false,
)