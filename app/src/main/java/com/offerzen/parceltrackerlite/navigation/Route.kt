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
        route = "shipment_detail/{id}",
        title = "Shipment Details"
    ) {
        fun createRoute(id: String) = "shipment_detail/$id"
    }
}