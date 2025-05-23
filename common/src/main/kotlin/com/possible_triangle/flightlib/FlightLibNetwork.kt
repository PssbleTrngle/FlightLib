package com.possible_triangle.flightlib

import com.mojang.serialization.Codec
import com.possible_triangle.flightlib.api.FlightKey
import com.possible_triangle.flightlib.logic.network.KeyPressedEvent
import com.possible_triangle.flightlib.logic.network.KeysSyncEvent
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.util.StringRepresentable

object FlightLibNetwork {

    val KEY_PRESSED = KeyPressedEvent.register()
    val KEYS_SYNC = KeysSyncEvent.register()

    val KEYS_CODEC: Codec<Map<FlightKey, Boolean>> = Codec.unboundedMap(
        StringRepresentable.fromEnum(FlightKey::values),
        Codec.BOOL,
    )

    val KEYS_STREAM_CODEC: StreamCodec<FriendlyByteBuf, Map<FlightKey, Boolean>> =
        StreamCodec.of(FlightLibNetwork::encodeKeys, FlightLibNetwork::decodeKeys)

    private fun decodeKeys(buffer: FriendlyByteBuf) =
        buffer.readMap({ it.readEnum(FlightKey::class.java) }, FriendlyByteBuf::readBoolean)

    private fun encodeKeys(buffer: FriendlyByteBuf, keys: Map<FlightKey, Boolean>) {
        buffer.writeMap(keys, FriendlyByteBuf::writeEnum, FriendlyByteBuf::writeBoolean)
    }

    fun register() {
        // Loads this class
    }

}