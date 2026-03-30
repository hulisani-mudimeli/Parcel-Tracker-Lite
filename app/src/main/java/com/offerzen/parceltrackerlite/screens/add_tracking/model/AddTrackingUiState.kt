package com.offerzen.parceltrackerlite.screens.add_tracking.model

sealed interface AddTrackingUiState {
    object Idle : AddTrackingUiState
    object Loading : AddTrackingUiState
    data class Error(val errorMessage: String) : AddTrackingUiState
}