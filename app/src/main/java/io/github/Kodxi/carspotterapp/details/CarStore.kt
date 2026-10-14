package io.github.Kodxi.carspotterapp.details

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



@OptIn(ExperimentalUuidApi::class)
class CarStore : IStore {
    private val cars = arrayListOf(CarDetails(brandModel = "Name", description = "Word word word"), CarDetails(brandModel = "Name2", description = "Word2 word2 word2"))

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
            foundCar.description = car.description
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

    override fun getSize(): Int {
        return cars.size
    }
}
