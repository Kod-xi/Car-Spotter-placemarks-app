package org.setu.placemark.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface IStore {

    fun findAll(): List<CarDetails>
    fun create(placemark: CarDetails)
    fun update(placemark: CarDetails): Boolean
    fun delete(id: Uuid): Boolean
    fun findOne(id: Uuid): CarDetails?

}