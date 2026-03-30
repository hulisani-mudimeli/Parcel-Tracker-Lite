package com.offerzen.parceltrackerlite.screens.shipments_list.view.segments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.parceltrackerlite.screens.common.ShipmentStatusChip
import com.offerzen.parceltrackerlite.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentListSuccessState(
    shipments: List<ShipmentItemDb>,
    modifier: Modifier,
    onShipmentClick: (ShipmentItemDb) -> Unit,
    onFavoriteClick: (ShipmentItemDb) -> Unit,
    onAddClick: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean = false
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.zero),
    ) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (shipments.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tracked shipments yet.\nTap + to start tracking shipments.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = Dimensions.spacingDefault,
                            end = Dimensions.spacingDefault,
                            top = Dimensions.spacingDefault,
                            bottom = Dimensions.listBottomPaddingDefault
                        ),
                        verticalArrangement = Arrangement.spacedBy(Dimensions.spacingDefaultHalf),
                    ) {
                        items(
                            items = shipments,
                            key = { it.id },
                        ) { shipment ->
                            ShipmentItem(
                                shipment = shipment,
                                onShipmentClick = { onShipmentClick(shipment) },
                                onFavoriteClick = { onFavoriteClick(shipment) }
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimensions.spacingDefault)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add tracking number"
            )
        }
    }
}

@Composable
fun ShipmentItem(
    shipment: ShipmentItemDb,
    onShipmentClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onShipmentClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.spacingDefault),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = shipment.title ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Dimensions.textSpacingDefault))
                Text(
                    text = shipment.trackingNumber ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Dimensions.textSpacingDefault))
                Text(
                    text = shipment.carrier ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimensions.spacingDefaultHalf))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingDefaultHalf)
                ) {
                    ShipmentStatusChip(status = shipment.status)
                    Text(
                        text = shipment.lastUpdate ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // favorite button
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (shipment.favorite) Icons.Rounded.Favorite
                    else Icons.Rounded.FavoriteBorder,
                    contentDescription = if (shipment.favorite) "Unfavorite" else "Favorite",
                    tint = if (shipment.favorite) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}