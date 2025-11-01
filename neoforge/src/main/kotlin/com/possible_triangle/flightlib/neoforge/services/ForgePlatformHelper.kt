package com.possible_triangle.flightlib.neoforge.services

import com.possible_triangle.flightlib.platform.services.IPlatformHelper
import net.neoforged.fml.ModList
import net.neoforged.fml.loading.FMLLoader

class ForgePlatformHelper : IPlatformHelper {

    override val platformName = "NeoForge"

    override fun isModLoaded(modId: String) = ModList.get().isLoaded(modId)

    override val isDevelopmentEnvironment get() = !FMLLoader.isProduction()

}