package com.possible_triangle.flightlib.fabric

import com.possible_triangle.flightlib.FlightLibNetwork
import com.possible_triangle.flightlib.api.Constants
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate
import net.fabricmc.fabric.impl.attachment.AttachmentTypeImpl
import net.minecraft.resources.ResourceLocation

object FabricDataAttachments {

    @JvmField
    val SETTINGS_ATTACHMENT = AttachmentTypeImpl(
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "settings"),
        ::emptyMap,
        FlightLibNetwork.KEYS_CODEC,
        FlightLibNetwork.KEYS_STREAM_CODEC,
        AttachmentSyncPredicate.targetOnly(),
        true
    )

}