package io.github.Kodxi.carspotterapp.details

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.Kodxi.carspotterapp.main.carStore
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



@OptIn(ExperimentalUuidApi::class)
class CarStore(private val file: File) : IStore {
    private val cars = ArrayList<CarDetails>()
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    init {
        load()
    }

    override fun findAll(): List<CarDetails> {
        return cars
    }

    override fun create(car: CarDetails) {
        cars.add(car)
        save()
    }

    override fun update(car: CarDetails): Boolean {
        val foundCar = findOne(car.id)
        return if (foundCar != null) {
            foundCar.brandModel = car.brandModel
            foundCar.description = car.description
            foundCar.dateSpotted = car.dateSpotted
            foundCar.imageUri = car.imageUri
            foundCar.x = car.x
            foundCar.y = car.y
            save()
            true
        } else {
            false
        }
    }

    override fun delete(id: Uuid): Boolean {
        val foundPlacemark = findOne(id)
        return if (foundPlacemark != null) {
            cars.remove(foundPlacemark)
            save()
            true
        } else {
            false
        }
    }

    override fun findOne(id: Uuid): CarDetails? {
        return cars.find { p -> p.id == id }
    }

    private fun load() {
        if(!file.exists()) return

        val type = object : TypeToken<ArrayList<CarDetails>>() {}.type
        val loaded: ArrayList<CarDetails> =
            gson.fromJson(file.readText(), type) ?: arrayListOf()

        cars.clear()
        cars.addAll(loaded)

    }

    private fun save(){
        file.parentFile?.mkdirs()
        file.writeText(gson.toJson(cars))
    }
}
