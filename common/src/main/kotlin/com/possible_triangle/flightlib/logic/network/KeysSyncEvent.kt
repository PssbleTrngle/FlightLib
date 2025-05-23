package com.possible_triangle.flightlib.logic.network

import com.possible_triangle.flightlib.FlightLibNetwork
import com.possible_triangle.flightlib.api.Constants.MOD_ID
import com.possible_triangle.flightlib.api.FlightKey
import com.possible_triangle.flightlib.logic.ControlManager
import com.possible_triangle.flightlib.platform.Services
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player

class KeysSyncEvent(val keys: Map<FlightKey, Boolean>) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<KeysSyncEvent> = TYPE.type()

    companion object {
        private val TYPE = CustomPacketPayload.TypeAndCodec(
            CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(MOD_ID, "key_pressed")),
            FlightLibNetwork.KEYS_STREAM_CODEC.map(::KeysSyncEvent, KeysSyncEvent::keys)
        )

        fun register() = Services.NETWORK.serverToClient(TYPE, KeysSyncEvent::handle)
    }

    private fun handle(player: Player) {
        keys.forEach { key, pressed ->
            ControlManager.setKey(player, key, pressed)
        }
    }

}