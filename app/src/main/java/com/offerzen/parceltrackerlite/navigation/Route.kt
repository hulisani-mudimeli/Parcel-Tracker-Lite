package com.offerzen.parceltrackerlite.navigation

sealed class Route(val route: String, val title: String) {
    data object ShipmentList : Route(
        route = "shipment_list",
        title = "Tracked Shipments"
    )
    data object AddTracking : Route(
        route = "add_tracking",
        title = "Add Tracking Number"
    )
    data object ShipmentDetail : Route(
        route = "shipment_detail/{trackingNumber}",
        title = "Shipment Details"
    ) {
        fun createRoute(trackingNumber: String) = "shipment_detail/$trackingNumber"
    }
}