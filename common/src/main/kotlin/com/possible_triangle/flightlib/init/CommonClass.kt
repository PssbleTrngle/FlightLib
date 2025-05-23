package com.possible_triangle.flightlib.init

import com.possible_triangle.flightlib.FlightLibNetwork
import com.possible_triangle.flightlib.api.IFlightApi
import com.possible_triangle.flightlib.platform.Services

object CommonClass {

    val SOUND_WHOOSH = Services.REGISTRIES.registerSound("whoosh")

    @JvmStatic
    fun init() {
        IFlightApi.register(FlightApiImpl)
        FlightLibNetwork.register()
        CommonSources.register()
    }

    @JvmStatic
    fun clientInit() {

    }

}