package com.offerzen.parceltrackerlite

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.offerzen.domain.usecases.AddTrackingNumberUseCase
import com.offerzen.domain.usecases.FetchShipmentDetailUseCase
import com.offerzen.domain.usecases.FetchTrackedShipmentListUseCase
import com.offerzen.domain.usecases.MarkParcelAsFavoriteUseCase
import com.offerzen.domain.usecases.RefreshTrackedShipmentsUseCase
import com.offerzen.parceltrackerlite.ui.theme.ParcelTrackerLiteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val TAG = "MainActivityTAG"

    @Inject lateinit var addTrackNumberUseCase: AddTrackingNumberUseCase
    @Inject lateinit var fetchTrackedShipmentListUseCase: FetchTrackedShipmentListUseCase
    @Inject lateinit var fetchShipmentDetailUseCase: FetchShipmentDetailUseCase
    @Inject lateinit var markParcelAsFavoriteUseCase: MarkParcelAsFavoriteUseCase
    @Inject lateinit var refreshTrackedShipmentsUseCase: RefreshTrackedShipmentsUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcelTrackerLiteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        lifecycleScope.launch {
//            val result = addTrackNumberUseCase("1Z999AA10123456784")
//            val result = fetchShipmentDetailUseCase("9400111899223197428499")
//            val result = markParcelAsFavoriteUseCase("GM555555555US", true)
//            val result = refreshTrackedShipmentsUseCase()
            val result = fetchTrackedShipmentListUseCase()
            Log.d(TAG, "onCreate - result: $result")
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