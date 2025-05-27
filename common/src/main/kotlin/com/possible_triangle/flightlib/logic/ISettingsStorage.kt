package com.possible_triangle.flightlib.logic

import com.mojang.serialization.Codec
import com.possible_triangle.flightlib.api.FlightKey
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.util.StringRepresentable

typealias JetpackSettings = Map<FlightKey, Boolean>

interface ISettingsStorage {

    fun `flightlib$set`(settings: JetpackSettings)

    fun `flightlib$get`(): JetpackSettings

    companion object {

        private fun filter(settings: JetpackSettings) = settings.filterKeys { it.toggle }

        val KEYS_CODEC: Codec<JetpackSettings> = Codec.unboundedMap(
            StringRepresentable.fromEnum(FlightKey::values),
            Codec.BOOL.orElse(false),
        ).xmap(::filter, ::filter)

        val KEYS_STREAM_CODEC: StreamCodec<FriendlyByteBuf, JetpackSettings> =
            StreamCodec.of(ISettingsStorage::encodeKeys, ISettingsStorage::decodeKeys)

        private fun decodeKeys(buffer: FriendlyByteBuf): JetpackSettings =
            buffer.readMap({ it.readEnum(FlightKey::class.java) }, FriendlyByteBuf::readBoolean)

        private fun encodeKeys(buffer: FriendlyByteBuf, keys: JetpackSettings) {
            buffer.writeMap(keys, FriendlyByteBuf::writeEnum, FriendlyByteBuf::writeBoolean)
        }
    }

}

fun ISettingsStorage.isPressed(key: FlightKey): Boolean = this.`flightlib$get`()[key] ?: false

fun ISettingsStorage.setKey(key: FlightKey, pressed: Boolean) {
    val keys = `flightlib$get`().toMutableMap()
    keys[key] = pressed
    `flightlib$set`(keys)
}