package com.offerzen.domain.usecases

import com.offerzen.common.result.MyResult
import com.offerzen.domain.repositories.ShipmentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MarkParcelAsFavoriteUseCaseTest {

    private lateinit var repository: ShipmentRepository
    private lateinit var useCase: MarkParcelAsFavoriteUseCase

    private val trackingNumber = "1Z999AA10123456784"

    @Before
    fun setup() {
        repository = mock()
        useCase = MarkParcelAsFavoriteUseCase(repository)
    }

    @Test
    fun `invoke marks shipment as favorite successfully`() = runTest {
        whenever(repository.markParcelAsFavorite(trackingNumber, true))
            .thenReturn(MyResult.Success(Unit))

        val result = useCase(trackingNumber, true)

        assertTrue(result is MyResult.Success)
        verify(repository).markParcelAsFavorite(trackingNumber, true)
    }

    @Test
    fun `invoke unmarks shipment as favorite successfully`() = runTest {
        whenever(repository.markParcelAsFavorite(trackingNumber, false))
            .thenReturn(MyResult.Success(Unit))

        val result = useCase(trackingNumber, false)

        assertTrue(result is MyResult.Success)
        verify(repository).markParcelAsFavorite(trackingNumber, false)
    }

    @Test
    fun `invoke returns error when marking favorite fails`() = runTest {
        whenever(repository.markParcelAsFavorite(trackingNumber, true))
            .thenReturn(MyResult.Error(Exception("Database error")))

        val result = useCase(trackingNumber, true)

        assertTrue(result is MyResult.Error)
    }
}