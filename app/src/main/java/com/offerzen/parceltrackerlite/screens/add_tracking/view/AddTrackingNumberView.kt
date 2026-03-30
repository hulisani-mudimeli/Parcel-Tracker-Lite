package com.offerzen.parceltrackerlite.screens.add_tracking.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.offerzen.parceltrackerlite.screens.add_tracking.model.AddTrackingUiState
import com.offerzen.parceltrackerlite.screens.add_tracking.viewmodel.AddTrackingNumberViewModel
import com.offerzen.parceltrackerlite.ui.theme.Dimensions


@Composable
fun AddTrackingNumberView(
    navController: NavController,
    viewModel: AddTrackingNumberViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddTrackingNumberContent(
        uiState,
        submitTrackingNumber = {
            viewModel.addTracking(
                trackingNumber = it,
                onAddTrackingSuccess = { navController.popBackStack() }
            )
        }
    )
}

@Composable
fun AddTrackingNumberContent(uiState: AddTrackingUiState, submitTrackingNumber: (String) -> Unit) {
    var trackingNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.spacingDefault),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacingDefault)
    ) {
        OutlinedTextField(
            value = trackingNumber,
            onValueChange = { trackingNumber = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Tracking Number") },
            placeholder = { Text("e.g. 1Z999AA10123456784") },
            trailingIcon = {
                if (trackingNumber.isNotEmpty()) {
                    IconButton(onClick = { trackingNumber = "" }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            isError = uiState is AddTrackingUiState.Error,
            supportingText = {
                when (val state = uiState) {
                    is AddTrackingUiState.Error -> Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                    else -> Unit
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { submitTrackingNumber(trackingNumber) }
            ),
            shape = MaterialTheme.shapes.medium
        )

        Button(
            onClick = { submitTrackingNumber(trackingNumber) },
            modifier = Modifier.fillMaxWidth(),
            enabled = trackingNumber.isNotBlank() && uiState !is AddTrackingUiState.Loading
        ) {
            if (uiState is AddTrackingUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.spacingDefault),
                    strokeWidth = Dimensions.strokeWidthDefault,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Add Tracking Number")
            }
        }
    }
}