package com.offerzen.parceltrackerlite.screens.shipment_details.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.offerzen.parceltrackerlite.screens.shipment_details.model.ShipmentDetailUiState
import com.offerzen.parceltrackerlite.screens.shipment_details.view.segments.CheckpointItem
import com.offerzen.parceltrackerlite.screens.shipment_details.view.segments.ShipmentHeaderCard
import com.offerzen.parceltrackerlite.screens.shipment_details.view.segments.ShipmentRouteCard
import com.offerzen.parceltrackerlite.screens.shipment_details.viewmodel.ShipmentDetailViewModel
import com.offerzen.parceltrackerlite.ui.theme.Dimensions

@Composable
fun ShipmentDetailsView (
    trackingNumber: String,
    viewModel: ShipmentDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadShipmentDetails(trackingNumber)
    }

    ShipmentDetailContent(
        uiState,
        onRetry = { viewModel.retryLoading(trackingNumber) }
    )
}

@Composable
fun ShipmentDetailContent(
    uiState: ShipmentDetailUiState,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    when (uiState) {
        is ShipmentDetailUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        is ShipmentDetailUiState.Success -> {
            val shipment = uiState.shipmentDetails

            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = Dimensions.spacingDefault,
                    end = Dimensions.spacingDefault,
                    top = Dimensions.spacingDefault,
                    bottom = Dimensions.spacingDefault
                ),
                verticalArrangement = Arrangement.spacedBy(Dimensions.spacingDefault)
            ) {
                // header card
                item {
                    ShipmentHeaderCard(shipment = shipment)
                }

                // route card
                if (shipment.origin != null || shipment.destination != null) {
                    item {
                        ShipmentRouteCard(
                            origin = shipment.origin,
                            destination = shipment.destination
                        )
                    }
                }

                // checkpoints timeline
                if (!shipment.checkpoints.isNullOrEmpty()) {
                    item {
                        Text(
                            text = "Timeline",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    val sorted = shipment.checkpoints!!.sortedByDescending { it.time }
                    itemsIndexed(
                        items = sorted,
                        key = { index, _ -> index }
                    ) { index, checkpoint ->
                        CheckpointItem(
                            checkpoint = checkpoint,
                            isLast = index == sorted.lastIndex
                        )
                    }
                }
            }
        }

        is ShipmentDetailUiState.Error -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(Dimensions.spacingDefault))
                Text(
                    text = "Something went wrong",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Dimensions.spacingDefaultHalf))
                Text(
                    text = uiState.errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Dimensions.spacingDefaultDouble)
                )
                if (onRetry != null) {
                    Spacer(modifier = Modifier.height(Dimensions.spacingSecondary))
                    Button(onClick = onRetry) {
                        Text(text = "Retry")
                    }
                }
            }
        }

    }

}