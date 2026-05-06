package com.possible_triangle.flightlib.platform

import com.possible_triangle.flightlib.platform.services.INetwork
import com.possible_triangle.flightlib.platform.services.IPlatformHelper
import com.possible_triangle.flightlib.platform.services.IRegistries
import java.util.*

object Services {
    val PLATFORM = load(IPlatformHelper::class.java)
    val NETWORK = load(INetwork::class.java)
    val REGISTRIES = load(IRegistries::class.java)

    private fun <T> load(clazz: Class<T>): T {
        val loadedService: T =
            ServiceLoader
                .load(clazz, Services::class.java.classLoader)
                .findFirst()
                .orElseThrow { NullPointerException("Failed to load service for ${clazz.name}") }
        return loadedService
    }
}
