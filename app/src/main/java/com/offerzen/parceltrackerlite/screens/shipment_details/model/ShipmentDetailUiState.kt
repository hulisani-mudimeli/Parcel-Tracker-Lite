package com.offerzen.parceltrackerlite.screens.shipment_details.model

import com.offerzen.common.models.network.ShipmentDetails

sealed interface ShipmentDetailUiState {
    object Loading : ShipmentDetailUiState
    data class Success(val shipmentDetails: ShipmentDetails) : ShipmentDetailUiState
    data class Error(val errorMessage: String) : ShipmentDetailUiState
}