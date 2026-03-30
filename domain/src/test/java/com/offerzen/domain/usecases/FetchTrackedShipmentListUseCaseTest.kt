package com.offerzen.domain.usecases

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FetchTrackedShipmentListUseCaseTest {

    private lateinit var repository: ShipmentRepository
    private lateinit var useCase: FetchTrackedShipmentListUseCase

    private val mockShipments = listOf(
        ShipmentItemDb(trackingNumber = "1Z999AA10123456784", title = "Laptop order", favorite = false),
        ShipmentItemDb(trackingNumber = "9400111899223197428499", title = "Shoes", favorite = true),
        ShipmentItemDb(trackingNumber = "EV123456789CN", title = "Camera", favorite = false),
    )

    @Before
    fun setup() {
        repository = mock()
        useCase = FetchTrackedShipmentListUseCase(repository)
    }

    @Test
    fun `invoke returns full list when no filters applied`() = runTest {
        whenever(repository.fetchTrackedShipmentList(null, null))
            .thenReturn(mockShipments)

        val result = useCase()

        assertEquals(3, result.size)
        verify(repository).fetchTrackedShipmentList(null, null)
    }

    @Test
    fun `invoke returns empty list when no shipments tracked`() = runTest {
        whenever(repository.fetchTrackedShipmentList(null, null))
            .thenReturn(emptyList())

        val result = useCase()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke returns filtered list when query provided`() = runTest {
        val query = "Laptop"
        val filtered = listOf(mockShipments[0])
        whenever(repository.fetchTrackedShipmentList(query, null))
            .thenReturn(filtered)

        val result = useCase(query = query)

        assertEquals(1, result.size)
        assertEquals("Laptop order", result[0].title)
    }

    @Test
    fun `invoke returns only favorites when favorite filter is true`() = runTest {
        val favorites = listOf(mockShipments[1])
        whenever(repository.fetchTrackedShipmentList(null, true))
            .thenReturn(favorites)

        val result = useCase(favorite = true)

        assertEquals(1, result.size)
        assertTrue(result[0].favorite)
    }

    @Test
    fun `invoke passes both query and favorite filter to repository`() = runTest {
        whenever(repository.fetchTrackedShipmentList("Shoes", true))
            .thenReturn(listOf(mockShipments[1]))

        val result = useCase(query = "Shoes", favorite = true)

        assertEquals(1, result.size)
        verify(repository).fetchTrackedShipmentList("Shoes", true)
    }
}