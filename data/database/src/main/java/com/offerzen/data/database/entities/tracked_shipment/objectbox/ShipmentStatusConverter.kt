package com.offerzen.data.database.entities.tracked_shipment.objectbox

import com.offerzen.common.enums.ShipmentStatus
import io.objectbox.converter.PropertyConverter

class ShipmentStatusConverter : PropertyConverter<ShipmentStatus, String> {
    override fun convertToEntityProperty(databaseValue: String): ShipmentStatus {
        return ShipmentStatus.Companion.getByCode(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: ShipmentStatus): String {
        return entityProperty.code
    }
}