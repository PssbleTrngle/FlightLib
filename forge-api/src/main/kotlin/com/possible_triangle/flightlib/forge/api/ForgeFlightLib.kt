package com.possible_triangle.flightlib.forge.api

import com.possible_triangle.flightlib.api.Constants
import com.possible_triangle.flightlib.api.IJetpack
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.capabilities.EntityCapability
import net.neoforged.neoforge.capabilities.ItemCapability

object ForgeFlightLib {

    @JvmField
    val ITEM_CAPABILITY: ItemCapability<IJetpack, Void?> = ItemCapability.createVoid(
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jetpack"),
        IJetpack::class.java,
    )

    @JvmField
    val ENTITY_CAPABILITY: EntityCapability<IJetpack, Void?> = EntityCapability.createVoid(
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jetpack"),
        IJetpack::class.java,
    )

}