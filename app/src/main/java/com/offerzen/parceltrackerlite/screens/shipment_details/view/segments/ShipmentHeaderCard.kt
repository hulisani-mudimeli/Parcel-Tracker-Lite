package com.offerzen.parceltrackerlite.screens.shipment_details.view.segments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.offerzen.common.enums.ShipmentStatus
import com.offerzen.common.models.network.ShipmentDetails
import com.offerzen.parceltrackerlite.screens.common.ShipmentStatusChip
import com.offerzen.parceltrackerlite.ui.theme.Dimensions

@Composable
fun ShipmentHeaderCard(
    shipment: ShipmentDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.spacingDefault),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacingDefaultHalf)
        ) {
            shipment.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            shipment.carrier?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            shipment.trackingNumber?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(Dimensions.textSpacingDefault))
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingDefaultHalf),
                verticalAlignment = Alignment.CenterVertically
            ) {
                shipment.status?.let { ShipmentStatusChip(status = ShipmentStatus.getByCode(it)) }
            }
            shipment.eta?.let {
                Spacer(modifier = Modifier.height(Dimensions.textSpacingDefault))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.textSpacingDefault),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.spacingDefault),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "ETA: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            shipment.lastUpdate?.let {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.textSpacingDefault),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.spacingDefault),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Last update: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}