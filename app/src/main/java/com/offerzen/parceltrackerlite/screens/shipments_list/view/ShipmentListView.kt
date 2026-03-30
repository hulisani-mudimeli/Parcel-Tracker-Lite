package com.offerzen.parceltrackerlite.screens.shipments_list.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.offerzen.common.enums.ShipmentStatus
import com.offerzen.common.helpers.time_formatter.DefaultTimeFormatter
import com.offerzen.common.helpers.time_formatter.TimeFormatter
import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.parceltrackerlite.navigation.Route
import com.offerzen.parceltrackerlite.screens.shipments_list.model.ShipmentListUiState
import com.offerzen.parceltrackerlite.screens.shipments_list.view.segments.ShipmentErrorContent
import com.offerzen.parceltrackerlite.screens.shipments_list.view.segments.ShipmentListSuccessState
import com.offerzen.parceltrackerlite.screens.shipments_list.view.segments.ShipmentLoadingContent
import com.offerzen.parceltrackerlite.screens.shipments_list.viewmodel.ShipmentListViewModel
import com.offerzen.parceltrackerlite.ui.PhonePreviews

@Composable
fun ShipmentListView(
    navController: NavController,
    viewModel: ShipmentListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val timeFormatter = viewModel.timeFormatter

    LaunchedEffect(navBackStackEntry) {
        viewModel.onRetryFetch()
    }

    ShipmentListContent(
        uiState,
        timeFormatter,
        onRetry = { viewModel.onRetryFetch() },
        onShipmentClick = {
            it.trackingNumber?.let {
                navController.navigate(Route.ShipmentDetail.createRoute(it))
            }
        },
        onFavoriteClick = { viewModel.onFavoriteClick(it) },
        onAddClick = { navController.navigate(Route.AddTracking.route) },
        onRefresh = { viewModel.onRefresh() }
    )
}

@Composable
fun ShipmentListContent(
    uiState: ShipmentListUiState,
    timeFormatter: TimeFormatter,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onShipmentClick: (ShipmentItemDb) -> Unit,
    onFavoriteClick: (ShipmentItemDb) -> Unit,
    onAddClick: () -> Unit,
    onRefresh: () -> Unit,
) {
    when (uiState) {
        is ShipmentListUiState.Loading -> ShipmentLoadingContent(modifier)
        is ShipmentListUiState.Error ->
            ShipmentErrorContent(
                errorMessage = uiState.message,
                modifier = modifier,
                onRetry = onRetry
            )

        is ShipmentListUiState.Success ->
            ShipmentListSuccessState(
                uiState.shipments,
                timeFormatter,
                modifier,
                onShipmentClick,
                onFavoriteClick,
                onAddClick,
                onRefresh,
                uiState.isRefreshing
            )
    }
}

@PhonePreviews
@Composable
fun ShipmentListContentPreview2() {
    ShipmentListContent(
        ShipmentListUiState.Success(listOf(
            ShipmentItemDb(1, "trk02123d", "989213213", "Acme Express", "Laptop order#2", ShipmentStatus.InTransit, "2026-02-25T14:12:00Z", false),
            ShipmentItemDb(2, "trk342342", "1safsdkj3", "Universal Post", "Shoes#2", ShipmentStatus.Delivered, "2026-02-19T08:01:00Z", true)
        )),
        DefaultTimeFormatter(),
        onRetry = {},
        onShipmentClick = {},
        onFavoriteClick = {},
        onAddClick = {},
        onRefresh = {}
    )
}