package com.offerzen.common.enums


enum class ShipmentStatus(val code: String, val label: String) {
    Created("created", "Created"),
    PickedUp("picked_up", "Picked Up"),
    InTransit("in_transit", "In Transit"),
    OutForDelivery("out_for_delivery", "Out For Delivery"),
    Delivered("delivered", "Delivered"),
    Exception("exception", "Exception"),
    Unknown("unknown", "Unknown");

    companion object {
        fun getByCode(code: String?): ShipmentStatus {
            return ShipmentStatus.entries.find { it.code == code } ?: Unknown
        }
    }
}






