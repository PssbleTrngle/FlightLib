package com.possible_triangle.flightlib.neoforge

import com.possible_triangle.flightlib.api.IFlightApi
import com.possible_triangle.flightlib.neoforge.api.NeoForgeFlightLib
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

object ForgeSources {

    @JvmStatic
    fun register() {
        IFlightApi.INSTANCE.addSourceCaster {
            listOf {
                when (it) {
                    is ItemStack -> it.getCapability(NeoForgeFlightLib.ITEM_CAPABILITY)
                    is Entity -> it.getCapability(NeoForgeFlightLib.ENTITY_CAPABILITY)
                    else -> null
                }
            }
        }
    }

}