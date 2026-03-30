package com.offerzen.data.database.entities.tracked_shipment.objectbox

import com.offerzen.common.enums.ShipmentStatus
import io.objectbox.annotation.Convert
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
    @Convert(converter = ShipmentStatusConverter::class, dbType = String::class)
    var status: ShipmentStatus = ShipmentStatus.Unknown,
    var lastUpdate: String? = null,
    var favorite: Boolean = false,
)