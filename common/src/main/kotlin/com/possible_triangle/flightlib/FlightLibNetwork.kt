package com.possible_triangle.flightlib

import com.possible_triangle.flightlib.logic.network.KeyPressedEvent
import com.possible_triangle.flightlib.logic.network.KeysSyncEvent

object FlightLibNetwork {
    val KEY_PRESSED = KeyPressedEvent.register()
    val KEYS_SYNC = KeysSyncEvent.register()

    fun register() {
        // Loads this class
    }
}
