package com.possible_triangle.flightlib.logic

import com.possible_triangle.flightlib.api.FlightKey

typealias JetpackSettings = Map<FlightKey, Boolean>

interface ISettingsStorage {

    fun `flightlib$set`(settings: JetpackSettings)

    fun `flightlib$get`(): JetpackSettings

}

fun ISettingsStorage.isPressed(key: FlightKey): Boolean = this.`flightlib$get`()[key] ?: false

fun ISettingsStorage.setKey(key: FlightKey, pressed: Boolean) {
    val keys = `flightlib$get`().toMutableMap()
    keys[key] = pressed
    `flightlib$set`(keys)
}