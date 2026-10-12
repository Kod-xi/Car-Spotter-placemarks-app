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
    var decription: String = "",
    var dateSpotted: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    var x: Double = 0.0,
    var y: Double = 0.0
)