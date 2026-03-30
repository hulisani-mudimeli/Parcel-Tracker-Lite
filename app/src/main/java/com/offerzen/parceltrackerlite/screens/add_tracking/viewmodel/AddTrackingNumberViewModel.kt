package com.offerzen.parceltrackerlite.screens.add_tracking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.offerzen.common.result.MyResult
import com.offerzen.domain.usecases.AddTrackingNumberUseCase
import com.offerzen.parceltrackerlite.screens.add_tracking.model.AddTrackingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTrackingNumberViewModel @Inject constructor(
    private val addTrackingNumberUseCase: AddTrackingNumberUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddTrackingUiState>(AddTrackingUiState.Idle)
    val uiState: StateFlow<AddTrackingUiState> = _uiState.asStateFlow()


    fun addTracking(trackingNumber: String, onAddTrackingSuccess: () -> Unit) {
        _uiState.value = AddTrackingUiState.Loading

        viewModelScope.launch {
            val result = addTrackingNumberUseCase(trackingNumber)
            when (result) {
                is MyResult.Success -> {
                    _uiState.value = AddTrackingUiState.Idle
                    onAddTrackingSuccess()
                }
                is MyResult.Error -> {
                    _uiState.value = AddTrackingUiState.Error(result.cause.message ?: "Unknown error")
                }
            }
        }
    }
}