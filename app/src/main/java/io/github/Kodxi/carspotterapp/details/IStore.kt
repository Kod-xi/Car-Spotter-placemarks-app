package io.github.Kodxi.carspotterapp.details

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface IStore {

    fun findAll(): List<CarDetails>
    fun create(car: CarDetails)
    fun update(car: CarDetails): Boolean
    fun delete(id: Uuid): Boolean
    fun findOne(id: Uuid): CarDetails?
    fun getSize(): Int

}