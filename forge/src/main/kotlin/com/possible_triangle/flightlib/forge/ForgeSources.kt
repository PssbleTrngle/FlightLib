package com.possible_triangle.flightlib.forge

import com.possible_triangle.flightlib.api.IFlightApi
import com.possible_triangle.flightlib.forge.api.ForgeFlightLib
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

object ForgeSources {

    @JvmStatic
    fun register() {
        IFlightApi.INSTANCE.addSourceCaster {
            listOf {
                when (it) {
                    is ItemStack -> it.getCapability(ForgeFlightLib.ITEM_CAPABILITY)
                    is Entity -> it.getCapability(ForgeFlightLib.ENTITY_CAPABILITY)
                    else -> null
                }
            }
        }
    }

}