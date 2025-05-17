package com.possible_triangle.flightlib.forge

import com.possible_triangle.flightlib.api.IFlightApi
import com.possible_triangle.flightlib.forge.api.ForgeFlightLib
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

object ForgeSources {

    fun register() {
        IFlightApi.INSTANCE.addSourceCaster {
            listOf {
                if (it is ItemStack) it.getCapability(ForgeFlightLib.ITEM_CAPABILITY)
                if (it is Entity) it.getCapability(ForgeFlightLib.ENTITY_CAPABILITY)
                else null
            }
        }
    }

}