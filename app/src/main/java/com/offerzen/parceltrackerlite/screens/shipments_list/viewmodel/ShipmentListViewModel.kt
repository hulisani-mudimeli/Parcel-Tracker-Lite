package com.offerzen.parceltrackerlite.screens.shipments_list.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.result.MyResult
import com.offerzen.domain.usecases.FetchTrackedShipmentListUseCase
import com.offerzen.domain.usecases.MarkParcelAsFavoriteUseCase
import com.offerzen.domain.usecases.RefreshTrackedShipmentsUseCase
import com.offerzen.parceltrackerlite.screens.shipments_list.model.ShipmentListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val fetchTrackedShipmentsUseCase: FetchTrackedShipmentListUseCase,
    private val markParcelAsFavoriteUseCase: MarkParcelAsFavoriteUseCase,
    private val refreshTrackedShipmentsUseCase: RefreshTrackedShipmentsUseCase
) : ViewModel() {
    private val TAG = ShipmentListViewModel::class.java.name
    private val _uiState = MutableStateFlow<ShipmentListUiState>(ShipmentListUiState.Loading)
    val uiState: StateFlow<ShipmentListUiState> = _uiState.asStateFlow()

    init {
        fetchShipments()
    }

    private fun fetchShipments() {
        viewModelScope.launch {
            delay(500.milliseconds) // simulate delay & show loading state
            val result = fetchTrackedShipmentsUseCase()
            Log.d(TAG, "fetchShipments: $result")

            _uiState.value = ShipmentListUiState.Success(result)
        }
    }

    fun onRetryFetch() {
        _uiState.value = ShipmentListUiState.Loading
        fetchShipments()
    }

    fun onFavoriteClick(shipment: ShipmentItemDb) {
        shipment.trackingNumber?.let {
            viewModelScope.launch {
                delay(500.milliseconds) // simulate delay & show loading state
                val result = markParcelAsFavoriteUseCase(it, !shipment.favorite)
                Log.d(TAG, "onFavoriteClick: $result")
                when (result) {
                    is MyResult.Success -> {
                        val current = _uiState.value as? ShipmentListUiState.Success  ?: return@launch
                        _uiState.value = current.copy(
                            shipments = current.shipments.map { item ->
                                if (item.trackingNumber == shipment.trackingNumber)
                                    item.copy(favorite = !item.favorite)
                                else item
                            }
                        )
                    }

                    is MyResult.Error -> {}
                }
            }
        }

    }

    fun onRefresh() {
        val current = _uiState.value as? ShipmentListUiState.Success  ?: return
        _uiState.value = current.copy(isRefreshing = true)
        viewModelScope.launch {
            delay(500.milliseconds) // This fixes flaky animation if refresh duration happens too fast.
            val result = refreshTrackedShipmentsUseCase()
            _uiState.value = current.copy(isRefreshing = false)
            when (result) {
                is MyResult.Success -> fetchShipments()
                is MyResult.Error -> {}
            }
        }
    }


}