package com.possible_triangle.flightlib.logic.network

import com.possible_triangle.flightlib.api.Constants.MOD_ID
import com.possible_triangle.flightlib.api.FlightKey
import com.possible_triangle.flightlib.logic.ControlManager
import com.possible_triangle.flightlib.platform.Services
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

class KeyEvent(val key: FlightKey, val pressed: Boolean, val notify: Boolean = false) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<KeyEvent> = TYPE.type()

    companion object {
        private val TYPE = CustomPacketPayload.TypeAndCodec(
            CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(MOD_ID, "key_event")),
            StreamCodec.of<FriendlyByteBuf, KeyEvent>(KeyEvent::encode, KeyEvent::decode)
        )

        private fun decode(packet: FriendlyByteBuf): KeyEvent {
            val key = packet.readEnum(FlightKey::class.java)
            return KeyEvent(key, packet.readBoolean(), packet.readBoolean())
        }

        private fun encode(packet: FriendlyByteBuf, event: KeyEvent) {
            packet.writeEnum(event.key)
            packet.writeBoolean(event.pressed)
            packet.writeBoolean(event.notify)
        }

        val BUS = Services.NETWORK.clientToServer(TYPE, KeyEvent::handle)
    }

    private fun handle(player: ServerPlayer) {
        if (notify) player.sendSystemMessage(
            Component.translatable(
                "message.$MOD_ID.control.${key.name.lowercase()}",
                Component.translatable("message.$MOD_ID.control.${if (pressed) "on" else "off"}")
            ),
            true,
        )
        ControlManager.handle(player, this)
    }

}