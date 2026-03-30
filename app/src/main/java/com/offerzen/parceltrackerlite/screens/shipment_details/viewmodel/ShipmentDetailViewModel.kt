package com.offerzen.parceltrackerlite.screens.shipment_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.offerzen.domain.usecases.FetchShipmentDetailUseCase
import com.offerzen.parceltrackerlite.screens.shipment_details.model.ShipmentDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class ShipmentDetailViewModel @Inject constructor(
    private val fetchShipmentDetailUseCase: FetchShipmentDetailUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<ShipmentDetailUiState>(ShipmentDetailUiState.Loading)
    val uiState: StateFlow<ShipmentDetailUiState> = _uiState.asStateFlow()

    fun loadShipmentDetails(trackingNumber: String) {
        viewModelScope.launch {
            delay(500.milliseconds) // simulate delay & show loading state
            val result = fetchShipmentDetailUseCase(trackingNumber)
            if (result != null) {
                val shipmentDetails = result.copy(checkpoints = result.checkpoints?.sortedBy { it.time })
                _uiState.value = ShipmentDetailUiState.Success(shipmentDetails)
            } else {
                _uiState.value = ShipmentDetailUiState.Error("Failed to fetch shipment details")
            }
        }
    }

    fun retryLoading(trackingNumber: String) {
        _uiState.value = ShipmentDetailUiState.Loading
        loadShipmentDetails(trackingNumber)
    }
}