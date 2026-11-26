package io.github.Kodxi.carspotterapp

import android.content.Context
import io.github.Kodxi.carspotterapp.details.CarStore
import java.io.File

object AppData {

    lateinit var carStore: CarStore
        private set

    fun initialize(context: Context){
        val file = File(context.filesDir, "cars.json")
        carStore = CarStore(file)
    }
}