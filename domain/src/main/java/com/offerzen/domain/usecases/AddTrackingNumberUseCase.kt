package com.offerzen.domain.usecases

import com.offerzen.common.models.mappers.toDb
import com.offerzen.common.result.MyResult
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTrackingNumberUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(trackingNumber: String): MyResult<Unit> {
        return withContext(Dispatchers.IO) {
            val localShipmentItem = shipmentRepository.findTrackedShipmentByTrackingNumber(trackingNumber)
            if (localShipmentItem != null) {
                MyResult.Error(Exception("This parcel is already being tracked."))
            } else {
                val shipmentItem = shipmentRepository.fetchRemoteShipmentItem(trackingNumber)
                if (shipmentItem == null) {
                    MyResult.Error(Exception("Tracking number not found"))
                } else {
                    shipmentRepository.insertOrUpdateTrackedShipmentItem(shipmentItem.toDb())
                }
            }
        }
    }
}