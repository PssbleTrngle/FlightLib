package com.possible_triangle.flightlib.fabric

import com.possible_triangle.flightlib.FlightLibNetwork
import com.possible_triangle.flightlib.api.Constants
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate
import net.minecraft.resources.ResourceLocation

object FabricDataAttachments {

    @JvmField
    val SETTINGS_ATTACHMENT = AttachmentRegistry.create(
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "settings")
    ) {
        it.persistent(FlightLibNetwork.KEYS_CODEC)
        it.syncWith(FlightLibNetwork.KEYS_STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
        it.copyOnDeath()
        it.initializer(::emptyMap)
    }

}