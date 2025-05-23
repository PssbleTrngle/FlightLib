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

class KeyPressedEvent(val key: FlightKey, val pressed: Boolean, val notify: Boolean = false) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<KeyPressedEvent> = TYPE.type()

    companion object {
        private val TYPE = CustomPacketPayload.TypeAndCodec(
            CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(MOD_ID, "key_pressed")),
            StreamCodec.of<FriendlyByteBuf, KeyPressedEvent>(KeyPressedEvent::encode, KeyPressedEvent::decode)
        )

        private fun decode(buffer: FriendlyByteBuf): KeyPressedEvent {
            val key = buffer.readEnum(FlightKey::class.java)
            return KeyPressedEvent(key, buffer.readBoolean(), buffer.readBoolean())
        }

        private fun encode(buffer: FriendlyByteBuf, event: KeyPressedEvent) {
            buffer.writeEnum(event.key)
            buffer.writeBoolean(event.pressed)
            buffer.writeBoolean(event.notify)
        }

        fun register() = Services.NETWORK.clientToServer(TYPE, KeyPressedEvent::handle)
    }

    private fun handle(player: ServerPlayer) {
        if (notify) player.sendSystemMessage(
            Component.translatable(
                "message.$MOD_ID.control.${key.name.lowercase()}",
                Component.translatable("message.$MOD_ID.control.${if (pressed) "on" else "off"}")
            ),
            true,
        )

        ControlManager.setKey(player, key, pressed)
    }

}