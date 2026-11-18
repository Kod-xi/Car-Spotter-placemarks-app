package io.github.Kodxi.carspotterapp

import io.github.Kodxi.carspotterapp.details.CarDetails

interface OnCarListener {
    fun onCarClick(mark: CarDetails)

    fun onCarLongClick(mark: CarDetails): Boolean

}