package com.offerzen.data.database.entities.tracked_shipment.objectbox

import com.offerzen.common.models.database.ShipmentItemDb
import com.offerzen.common.result.Result
import com.offerzen.data.database.entities.tracked_shipment.ShipmentStorageService
import io.objectbox.Box
import io.objectbox.query.QueryBuilder

class DefaultShipmentStorageService(
    private val shipmentBox: Box<ShipmentItemOB>
): ShipmentStorageService {
    override suspend fun insertOrUpdate(shipment: ShipmentItemDb): Result<Unit> {
        return try {
            insertOrUpdateInternal(shipment)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun insertOrUpdateInternal(shipment: ShipmentItemDb) {
        val existingRecord: ShipmentItemDb? = findShipmentByTrackingNumber(shipment.trackingNumber)
        if (existingRecord != null) {
            shipment.id = existingRecord.id
            shipment.favorite = existingRecord.favorite
        }

        shipmentBox.put(shipment.toOB())
    }

    private fun findShipmentByTrackingNumber(trackingNumber: String?): ShipmentItemDb? {
        if (trackingNumber == null) return null

        val shipmentItemOB = shipmentBox.query().equal(
            ShipmentItemOB_.trackingNumber,
            trackingNumber,
            QueryBuilder.StringOrder.CASE_SENSITIVE
        ).build().findFirst()

        return shipmentItemOB?.toDb()
    }

    override suspend fun bulkInsertOrUpdateShipments(shipments: List<ShipmentItemDb>): Result<Unit> {
        return try {
            shipmentBox.store.runInTx {
                for (shipment in shipments) {
                    insertOrUpdateInternal(shipment)
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchShipmentList(
        query: String?,
        favorite: Boolean?
    ): List<ShipmentItemDb> {
        val shipments = shipmentBox.query().build().find()
        return shipments.map { it.toDb() }
    }

    override suspend fun markAsFavorite(
        trackingNumber: String,
        favorite: Boolean
    ): Result<Unit> {
        val shipment = findShipmentByTrackingNumber(trackingNumber)
        return if (shipment != null) {
            shipment.favorite = favorite
            try {
                insertOrUpdateInternal(shipment)
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        } else {
            Result.Error(Exception("Shipment provided is currently not tracked"))
        }
    }

}