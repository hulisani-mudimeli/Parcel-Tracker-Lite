package com.offerzen.domain.usecases

import com.offerzen.common.models.network.ShipmentCheckpoint
import com.offerzen.common.models.network.ShipmentDetails
import com.offerzen.common.models.network.ShipmentPlace
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FetchShipmentDetailUseCaseTest {

    private lateinit var repository: ShipmentRepository
    private lateinit var useCase: FetchShipmentDetailUseCase

    private val trackingNumber = "1Z999AA10123456784"
    private val mockShipmentDetails = ShipmentDetails(
        trackingNumber = trackingNumber,
        carrier = "Acme Express",
        title = "Laptop order",
        status = "in_transit",
        eta = "2026-03-05T18:00:00Z",
        lastUpdate = "2026-02-25T14:12:00Z",
        origin = ShipmentPlace(city = "Shenzhen", country = "CN"),
        destination = ShipmentPlace(city = "Austin", country = "US"),
        checkpoints = listOf(
            ShipmentCheckpoint(
                time = "2026-02-25T14:12:00Z",
                location = "Austin, US",
                status = "in_transit",
                message = "Departed facility"
            ),
            ShipmentCheckpoint(
                time = "2026-02-20T08:15:00Z",
                location = "Shenzhen, CN",
                status = "picked_up",
                message = "Package received"
            )
        )
    )

    @Before
    fun setup() {
        repository = mock()
        useCase = FetchShipmentDetailUseCase(repository)
    }

    @Test
    fun `invoke returns shipment details when found`() = runTest {
        whenever(repository.fetchRemoteShipmentDetails(trackingNumber))
            .thenReturn(mockShipmentDetails)

        val result = useCase(trackingNumber)

        assertEquals(mockShipmentDetails, result)
        assertEquals(trackingNumber, result?.trackingNumber)
    }

    @Test
    fun `invoke returns null when shipment details not found`() = runTest {
        whenever(repository.fetchRemoteShipmentDetails(trackingNumber))
            .thenReturn(null)

        val result = useCase(trackingNumber)

        assertNull(result)
    }

    @Test
    fun `invoke returns correct origin and destination`() = runTest {
        whenever(repository.fetchRemoteShipmentDetails(trackingNumber))
            .thenReturn(mockShipmentDetails)

        val result = useCase(trackingNumber)

        assertEquals("Shenzhen", result?.origin?.city)
        assertEquals("CN", result?.origin?.country)
        assertEquals("Austin", result?.destination?.city)
        assertEquals("US", result?.destination?.country)
    }

    @Test
    fun `invoke returns correct number of checkpoints`() = runTest {
        whenever(repository.fetchRemoteShipmentDetails(trackingNumber))
            .thenReturn(mockShipmentDetails)

        val result = useCase(trackingNumber)

        assertEquals(2, result?.checkpoints?.size)
    }
}