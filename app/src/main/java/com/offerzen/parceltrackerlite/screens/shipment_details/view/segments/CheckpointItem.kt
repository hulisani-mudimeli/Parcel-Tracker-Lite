package com.offerzen.parceltrackerlite.screens.shipment_details.view.segments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.offerzen.common.models.network.ShipmentCheckpoint
import com.offerzen.parceltrackerlite.screens.shipments_list.view.segments.ShipmentStatusChip
import com.offerzen.parceltrackerlite.ui.theme.Dimensions

@Composable
fun CheckpointItem(
    checkpoint: ShipmentCheckpoint,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingDefaultHalf)
    ) {
        // timeline indicator
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(Dimensions.spacingDefaultHalf)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(48.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
            }
        }

        // checkpoint info
        Column(
            modifier = Modifier.padding(bottom = if (isLast) 0.dp else Dimensions.spacingDefault),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            checkpoint.message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            checkpoint.location?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            checkpoint.time?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            checkpoint.status?.let {
                Spacer(modifier = Modifier.height(2.dp))
                ShipmentStatusChip(status = it)
            }
        }
    }
}