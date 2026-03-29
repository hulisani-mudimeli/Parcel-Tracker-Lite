package com.offerzen.domain.usecases

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTrackedShipmentListUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(
        query: String? = null,
        favorite: Boolean? = null
    ): List<ShipmentItemDb> {
        return withContext(Dispatchers.IO) {
            shipmentRepository.fetchTrackedShipmentList(query, favorite)
        }
    }
}