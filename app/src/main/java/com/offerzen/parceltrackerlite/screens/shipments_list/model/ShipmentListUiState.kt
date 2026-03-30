package com.offerzen.parceltrackerlite.screens.shipments_list.model

import com.offerzen.common.models.database.ShipmentItemDb

sealed interface ShipmentListUiState {
    object Loading : ShipmentListUiState
    data class Success(val shipments: List<ShipmentItemDb>, val isRefreshing: Boolean = false) : ShipmentListUiState
    data class Error(val message: String) : ShipmentListUiState
}