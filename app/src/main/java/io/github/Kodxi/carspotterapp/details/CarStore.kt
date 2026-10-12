package io.github.Kodxi.carspotterapp.details

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



@OptIn(ExperimentalUuidApi::class)
class CarStore : IStore {
    private val cars = ArrayList<CarDetails>()

    override fun findAll(): List<CarDetails> {
        return cars
    }

    override fun create(car: CarDetails) {
        cars.add(car)
    }

    override fun update(car: CarDetails): Boolean {
        val foundCar = findOne(car.id)
        return if (foundCar != null) {
            foundCar.brandModel = car.brandModel
            foundCar.decription = car.decription
            foundCar.dateSpotted = car.dateSpotted
            foundCar.x = car.x
            foundCar.y = car.y
            true
        } else {
            false
        }
    }

    override fun delete(id: Uuid): Boolean {
        val foundPlacemark = findOne(id)
        return if (foundPlacemark != null) {
            cars.remove(foundPlacemark)
            true
        } else {
            false
        }
    }

    override fun findOne(id: Uuid): CarDetails? {
        return cars.find { p -> p.id == id }
    }
}
