package io.github.Kodxi.carspotterapp.details

import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
data class CarDetails(
    var id: Uuid = Uuid.random(),
    var brandModel: String = "",
    var description: String = "",
    var dateSpotted: LocalDate? = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    var imageUri: String? = null,
    var x: Double = 0.0,
    var y: Double = 0.0
){
    fun validate() {
        require(brandModel.isNotBlank()){
            "Model must not be empty"
        }
        require(x.isFinite() && x in -90.0..90.0){

        }

        require(y.isFinite() && y in -90.0..90.0){
            "Longitude must be between -180 and 180."
        }
    }
}