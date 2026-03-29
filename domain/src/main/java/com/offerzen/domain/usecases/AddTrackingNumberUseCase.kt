package com.offerzen.domain.usecases

import com.offerzen.common.models.mappers.toDb
import com.offerzen.common.result.Result
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTrackingNumberUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(trackingNumber: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val shipmentItem = shipmentRepository.fetchRemoteShipmentItem(trackingNumber)
            if (shipmentItem == null) {
                Result.Error(Exception("Tracking number not found"))
            } else {
                shipmentRepository.insertOrUpdateTrackedShipmentItem(shipmentItem.toDb())
            }
        }
    }
}