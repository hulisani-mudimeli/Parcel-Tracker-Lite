package com.offerzen.domain.usecases

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.models.network.ShipmentItem
import com.offerzen.common.result.MyResult
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RefreshTrackedShipmentsUseCaseTest {

    private lateinit var repository: ShipmentRepository
    private lateinit var useCase: RefreshTrackedShipmentsUseCase

    private val trackedShipments = listOf(
        ShipmentItemDb(trackingNumber = "1Z999AA10123456784", title = "Laptop order"),
        ShipmentItemDb(trackingNumber = "9400111899223197428499", title = "Shoes"),
    )

    private val remoteShipments = listOf(
        ShipmentItem(
            id = "trk_8f3a9c",
            trackingNumber = "1Z999AA10123456784",
            carrier = "Acme Express",
            title = "Laptop order",
            status = "in_transit",
            lastUpdate = "2026-02-25T14:12:00Z"
        ),
        ShipmentItem(
            id = "trk_7aa1d2",
            trackingNumber = "9400111899223197428499",
            carrier = "Universal Post",
            title = "Shoes",
            status = "delivered",
            lastUpdate = "2026-02-19T08:01:00Z"
        )
    )

    @Before
    fun setup() {
        repository = mock()
        useCase = RefreshTrackedShipmentsUseCase(repository)
    }

    @Test
    fun `invoke fetches remote shipments for all tracked numbers and bulk updates`() = runTest {
        whenever(repository.fetchTrackedShipmentList())
            .thenReturn(trackedShipments)
        whenever(repository.fetchRemoteShipmentItems(any()))
            .thenReturn(remoteShipments)
        whenever(repository.bulkInsertOrUpdateTrackedShipmentItems(any()))
            .thenReturn(MyResult.Success(Unit))

        val result = useCase()

        assertTrue(result is MyResult.Success)
        verify(repository).fetchTrackedShipmentList()
        verify(repository).fetchRemoteShipmentItems(
            listOf("1Z999AA10123456784", "9400111899223197428499")
        )
        verify(repository).bulkInsertOrUpdateTrackedShipmentItems(any())
    }

    @Test
    fun `invoke does not call remote or update when no shipments are tracked`() = runTest {
        whenever(repository.fetchTrackedShipmentList())
            .thenReturn(emptyList())
        whenever(repository.fetchRemoteShipmentItems(emptyList()))
            .thenReturn(emptyList())
        whenever(repository.bulkInsertOrUpdateTrackedShipmentItems(any()))
            .thenReturn(MyResult.Success(Unit))

        val result = useCase()

        assertTrue(result is MyResult.Success)
        verify(repository).fetchRemoteShipmentItems(emptyList())
        verify(repository).bulkInsertOrUpdateTrackedShipmentItems(emptyList())
    }

    @Test
    fun `invoke returns error when bulk update fails`() = runTest {
        whenever(repository.fetchTrackedShipmentList())
            .thenReturn(trackedShipments)
        whenever(repository.fetchRemoteShipmentItems(any()))
            .thenReturn(remoteShipments)
        whenever(repository.bulkInsertOrUpdateTrackedShipmentItems(any()))
            .thenReturn(MyResult.Error(Exception("Database error")))

        val result = useCase()

        assertTrue(result is MyResult.Error)
    }

    @Test
    fun `invoke only passes non-null tracking numbers to remote fetch`() = runTest {
        val shipmentsWithNull = listOf(
            ShipmentItemDb(trackingNumber = "1Z999AA10123456784"),
            ShipmentItemDb(trackingNumber = null), // should be filtered out
        )
        whenever(repository.fetchTrackedShipmentList())
            .thenReturn(shipmentsWithNull)
        whenever(repository.fetchRemoteShipmentItems(listOf("1Z999AA10123456784")))
            .thenReturn(listOf(remoteShipments[0]))
        whenever(repository.bulkInsertOrUpdateTrackedShipmentItems(any()))
            .thenReturn(MyResult.Success(Unit))

        useCase()

        verify(repository).fetchRemoteShipmentItems(listOf("1Z999AA10123456784"))
    }
}