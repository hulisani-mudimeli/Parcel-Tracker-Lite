package com.offerzen.parceltrackerlite.screens.shipments_list.view.segments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.offerzen.parceltrackerlite.ui.theme.Dimensions

@Composable
fun ShipmentLoadingContent(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(Dimensions.progressIndicatorSizeDefault),
            color = MaterialTheme.colorScheme.primary
        )
    }
}