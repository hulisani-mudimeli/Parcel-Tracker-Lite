package com.offerzen.domain.usecases

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.models.network.ShipmentItem
import com.offerzen.common.result.MyResult
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AddTrackingNumberUseCaseTest {

    private lateinit var repository: ShipmentRepository
    private lateinit var useCase: AddTrackingNumberUseCase

    private val trackingNumber = "1Z999AA10123456784"
    private val mockShipmentItem = ShipmentItem(
        id = "trk_8f3a9c",
        trackingNumber = trackingNumber,
        carrier = "Acme Express",
        title = "Laptop order",
        status = "in_transit",
        lastUpdate = "2026-02-25T14:12:00Z"
    )

    @Before
    fun setup() {
        repository = mock()
        useCase = AddTrackingNumberUseCase(repository)
    }

    @Test
    fun `invoke returns error when shipment is already tracked`() = runTest {
        val existingItem = ShipmentItemDb(trackingNumber = trackingNumber)
        whenever(repository.findTrackedShipmentByTrackingNumber(trackingNumber))
            .thenReturn(existingItem)

        val result = useCase(trackingNumber)

        assertTrue(result is MyResult.Error)
        assertEquals("This parcel is already being tracked.", (result as MyResult.Error).cause.message)
        verify(repository, never()).fetchRemoteShipmentItem(any())
        verify(repository, never()).insertOrUpdateTrackedShipmentItem(any())
    }

    @Test
    fun `invoke returns error when tracking number not found remotely`() = runTest {
        whenever(repository.findTrackedShipmentByTrackingNumber(trackingNumber))
            .thenReturn(null)
        whenever(repository.fetchRemoteShipmentItem(trackingNumber))
            .thenReturn(null)

        val result = useCase(trackingNumber)

        assertTrue(result is MyResult.Error)
        assertEquals("Tracking number not found", (result as MyResult.Error).cause.message)
        verify(repository, never()).insertOrUpdateTrackedShipmentItem(any())
    }

    @Test
    fun `invoke inserts shipment when tracking number is valid and not already tracked`() = runTest {
        whenever(repository.findTrackedShipmentByTrackingNumber(trackingNumber))
            .thenReturn(null)
        whenever(repository.fetchRemoteShipmentItem(trackingNumber))
            .thenReturn(mockShipmentItem)
        whenever(repository.insertOrUpdateTrackedShipmentItem(any()))
            .thenReturn(MyResult.Success(Unit))

        val result = useCase(trackingNumber)

        assertTrue(result is MyResult.Success)
        verify(repository).insertOrUpdateTrackedShipmentItem(any())
    }

    @Test
    fun `invoke returns error when insert fails`() = runTest {
        whenever(repository.findTrackedShipmentByTrackingNumber(trackingNumber))
            .thenReturn(null)
        whenever(repository.fetchRemoteShipmentItem(trackingNumber))
            .thenReturn(mockShipmentItem)
        whenever(repository.insertOrUpdateTrackedShipmentItem(any()))
            .thenReturn(MyResult.Error(Exception("Database error")))

        val result = useCase(trackingNumber)

        assertTrue(result is MyResult.Error)
    }
}