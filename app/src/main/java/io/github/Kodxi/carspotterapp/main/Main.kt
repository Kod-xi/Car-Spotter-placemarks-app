@file:OptIn(
    kotlin.time.ExperimentalTime::class,
    kotlin.uuid.ExperimentalUuidApi::class
)

package io.github.Kodxi.carspotterapp.main

import io.github.Kodxi.carspotterapp.details.CarDetails
import io.github.Kodxi.carspotterapp.details.CarStore
import kotlinx.datetime.LocalDate
import kotlin.uuid.Uuid

val carStore = CarStore()

fun main() {
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addCar()
            2 -> updateCar()
            3 -> deleteCar()
            4 -> findCar()
            5 -> listAllCars()
            0 -> println("\nExiting application!")
            else -> println("\nTry again.")
        }
    } while (input != 0)
}

fun menu(): Int {
    println("""
        -----------------------
        | Choose an Option:
        -----------------------
        | 1: Add a Car
        | 2: Edit a Car
        | 3: Delete a Car
        | 4: Find a Car
        | 5: List all Cars
        -----------------------
        | 0: Exit Application
        -----------------------
    """.trimIndent())
    return readlnOrNull()?.toIntOrNull() ?: -1
}

fun addCar() {
    println("\n------- Add Car -------")
    print("Enter Car Brand / Model: ")
    val brandModel = readlnOrNull()?.trim().orEmpty()
    print("Enter Description: ")
    val description = readlnOrNull()?.trim().orEmpty()
    print("Enter Date Spotted")
    val dateSpotted = readlnOrNull()?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    }
    print("Enter X Coordinate")
    val xVal = readlnOrNull()?.toDoubleOrNull()
    print("Enter Y Coordinate")
    val yVal = readlnOrNull()?.toDoubleOrNull()

    if (brandModel.isNotEmpty()) {
        val car = CarDetails(brandModel = brandModel, description = description, dateSpotted = dateSpotted, x = xVal, y = yVal)

        carStore.create(car)
        println("Car added successfully with ID: ${car.id}")
    } else {
        println("Title cannot be empty. Creation cancelled.")
    }
}

fun listAllCars() {
    println("\n------ All Cars -------")
    val cars = carStore.findAll()
    if (cars.isEmpty()) {
        println("No placemarks stored yet.")
    } else {
        cars.forEach { println("ID: ${it.id} | Title: ${it.brandModel} | Description: ${it.description} | Date Spotted: ${it.dateSpotted} | (X,Y) Coords: (${it.x},${it.y})") }
    }
}

fun updateCar() {
    println("\n----- Update Car ------")
    listAllCars()
    if (carStore.findAll().isEmpty()) return

    print("\nEnter Car ID: ")
    val id = readlnOrNull()?.let {
        runCatching { Uuid.parse(it) }.getOrNull()
    }

    if((id?.let { carStore.findOne(it)}) == null ){
        println("No Car With That ID Was Found")
        return
    }

    print("Edit Brand / Model: ")
    val brandModel = readlnOrNull()?.trim().orEmpty()

    print("Edit Description: ")
    val description = readlnOrNull()?.trim().orEmpty()

    print("Edit Date Spotted")
    val dateSpotted = readlnOrNull()?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    }
    print("Edit X Coordinate")
    val xVal = readlnOrNull()?.toDoubleOrNull()
    print("Edit Y Coordinate")
    val yVal = readlnOrNull()?.toDoubleOrNull()

    if (brandModel.isEmpty()) {
        println("Cancelling, Brand / Model Was Empty")
        return
    }

    if (carStore.update(CarDetails(id, brandModel, description, dateSpotted, xVal, yVal))) {
        println("Car Updated Successfully")
    } else {
        println("Failed To Update Car")
    }
}

fun deleteCar() {
    println("\n----- Delete Car ------")
    listAllCars()
    if (carStore.findAll().isEmpty()) return

    print("\nEnter Car ID: ")
    val id = readlnOrNull()?.let {
        runCatching { Uuid.parse(it) }.getOrNull()
    }

    if (id != null) {
        val deleted = carStore.delete(id)
        if (deleted) {
            println("Car Deleted Successfully")
        } else {
            println("Failed To Delete Car")
        }
    } else {
        println("Invalid ID entered")
    }
}

fun findCar() {
    println("\n----- Search Car ------")
    print("\nEnter Car ID: ")
    val id = readlnOrNull()?.let {
        runCatching { Uuid.parse(it) }.getOrNull()
    }

    if (id != null) {
        val car = carStore.findOne(id)
        if (car != null) {
            println("Found: ID: ${car.id} | Title: ${car.brandModel} | Description: ${car.description} | Date Spotted: ${car.dateSpotted} | (X,Y) Coords: (${car.x},${car.y})")
        } else {
            println("No Car With That ID Was Found")
        }
    } else {
        println("Invalid ID entered")
    }
}
