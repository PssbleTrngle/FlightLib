package com.possible_triangle.flightlib.fabric

import com.possible_triangle.flightlib.api.IFlightApi
import com.possible_triangle.flightlib.api.IJetpack

object FabricSources {
    @JvmStatic
    fun register() {
        IFlightApi.INSTANCE.addSourceCaster {
            listOf {
                if (it is IJetpack) {
                    it
                } else {
                    null
                }
            }
        }
    }
}
