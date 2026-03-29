package com.offerzen.domain.usecases

import com.offerzen.common.models.mappers.toDb
import com.offerzen.common.result.Result
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshTrackedShipmentsUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val trackedShipments = shipmentRepository.fetchTrackedShipmentList()
            val latestRemoteShipmentsFromTracked = shipmentRepository.fetchRemoteShipmentItems(
                trackedShipments.mapNotNull { it.trackingNumber }
            )
            val latestTrackedShipmentsDb = latestRemoteShipmentsFromTracked.map { it.toDb() }

            shipmentRepository.bulkInsertOrUpdateTrackedShipmentItems(latestTrackedShipmentsDb)
        }
    }
}