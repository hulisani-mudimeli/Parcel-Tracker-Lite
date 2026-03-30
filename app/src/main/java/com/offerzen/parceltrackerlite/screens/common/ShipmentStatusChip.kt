package com.offerzen.parceltrackerlite.screens.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.offerzen.parceltrackerlite.ui.theme.Dimensions

@Composable
fun ShipmentStatusChip(
    status: String,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, label) = when (status) {
        "in_transit" -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            "In Transit"
        )
        "delivered" -> Triple(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer,
            "Delivered"
        )
        "out_for_delivery" -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            "Out for Delivery"
        )
        "exception" -> Triple(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer,
            "Exception"
        )
        "created" -> Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            "Created"
        )
        "picked_up" -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            "Picked Up"
        )
        else -> Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            "Unknown"
        )
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = Dimensions.spacingDefaultHalf, vertical = Dimensions.textSpacingDefault),
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}