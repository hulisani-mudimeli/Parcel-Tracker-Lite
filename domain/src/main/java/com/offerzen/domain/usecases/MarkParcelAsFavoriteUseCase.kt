package com.offerzen.domain.usecases

import com.offerzen.common.result.Result
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarkParcelAsFavoriteUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(trackingNumber: String, favorite: Boolean): Result<Unit> {
        return withContext(Dispatchers.IO) {
            shipmentRepository.markParcelAsFavorite(trackingNumber, favorite)
        }
    }
}