package com.offerzen.domain.usecases

import com.offerzen.common.models.network.ShipmentDetails
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchShipmentDetailUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(trackingNumber: String): ShipmentDetails? {
        return withContext(Dispatchers.IO) {
            shipmentRepository.fetchRemoteShipmentDetails(trackingNumber)
        }
    }
}