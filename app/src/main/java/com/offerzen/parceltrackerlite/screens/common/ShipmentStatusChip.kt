package com.offerzen.parceltrackerlite.screens.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.offerzen.common.enums.ShipmentStatus
import com.offerzen.parceltrackerlite.ui.theme.Dimensions

@Composable
fun ShipmentStatusChip(
    status: ShipmentStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (status) {
        ShipmentStatus.InTransit -> Pair(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
        )
        ShipmentStatus.Delivered -> Pair(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer,
        )
        ShipmentStatus.OutForDelivery,
        ShipmentStatus.PickedUp -> Pair(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
        )
        ShipmentStatus.Exception -> Pair(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer,
        )
        ShipmentStatus.Created,
        ShipmentStatus.Unknown -> Pair(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Text(
            text = status.label,
            modifier = Modifier.padding(horizontal = Dimensions.spacingDefaultHalf, vertical = Dimensions.textSpacingDefault),
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}