package com.offerzen.parceltrackerlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.offerzen.parceltrackerlite.navigation.Route
import com.offerzen.parceltrackerlite.screens.add_tracking.view.AddTrackingNumberView
import com.offerzen.parceltrackerlite.screens.shipment_details.view.ShipmentDetailsView
import com.offerzen.parceltrackerlite.screens.shipments_list.view.ShipmentListView
import com.offerzen.parceltrackerlite.ui.theme.ParcelTrackerLiteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val TAG = "MainActivityTAG"

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcelTrackerLiteTheme {
                val navController = rememberNavController()
                val routes = listOf(Route.ShipmentList, Route.AddTracking, Route.ShipmentDetail)

                val currentEntry by navController.currentBackStackEntryAsState()
                val currentRoute = routes.find {
                    it.route == currentEntry?.destination?.route
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = currentRoute?.title ?: "Parcel Tracker") },
                            navigationIcon = {
                                if (currentRoute != Route.ShipmentList) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            },
                            windowInsets = WindowInsets.statusBars,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.ShipmentList.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(Route.ShipmentList.route) {
                            ShipmentListView(navController)
                        }
                        composable(Route.AddTracking.route) {
                            AddTrackingNumberView(navController)
                        }
                        composable(Route.ShipmentDetail.route) { backStackEntry ->
                            val trackingNumber = backStackEntry.arguments?.getString("trackingNumber") ?: return@composable
                            ShipmentDetailsView(trackingNumber)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ParcelTrackerLiteTheme {
        Greeting("Android")
    }
}